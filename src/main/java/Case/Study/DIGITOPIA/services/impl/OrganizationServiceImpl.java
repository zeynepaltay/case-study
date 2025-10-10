package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.requests.OrganizationRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.mappers.OrganizationMapper;
import Case.Study.DIGITOPIA.mappers.UserMapper;
import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.User;
import Case.Study.DIGITOPIA.repositories.OrganizationRepository;
import Case.Study.DIGITOPIA.repositories.UserRepository;
import Case.Study.DIGITOPIA.services.OrganizationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Optional<OrganizationResponse> createOrganization(OrganizationRequest request) {
        if (organizationRepository.existsByRegistryNumber(request.getRegistryNumber())) {
            throw new EntityExistsException("That registry number already exists");
        }

        Organization organization = organizationMapper.toEntity(request);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());

        List<UUID> userIds = request.getUserId();

        if (userIds != null && !userIds.isEmpty()) {
            List<User> users = userRepository.findAllById(userIds);
            if (users.size() != userIds.size()) {
                throw new EntityNotFoundException("One or more users could not be found");
            }
            organization.setUsers(users);
        }

        Organization savedOrganization = organizationRepository.save(organization);
        return Optional.ofNullable(organizationMapper.toResponse(savedOrganization));
    }

    @Override
    @Transactional
    public Optional<OrganizationResponse> updateOrganization(UUID organizationId, OrganizationRequest request) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + organizationId));

        if (!Objects.equals(organization.getRegistryNumber(), request.getRegistryNumber())
                && organizationRepository.existsByRegistryNumber(request.getRegistryNumber())) {
            throw new IllegalStateException("That registry number already exists");
        }


        List<User> users = userRepository.findAllById(request.getUserId());

        organization.setName(request.getName());
        organization.setRegistryNumber(request.getRegistryNumber());
        organization.setEmail(request.getEmail());
        organization.setCompanySize(request.getCompanySize());
        organization.setFoundationYear(request.getFoundationYear());
        organization.setUpdatedAt(LocalDateTime.now());

        organization.setUsers(users);

        Organization savedOrganization = organizationRepository.save(organization);

        return Optional.ofNullable(organizationMapper.toResponse(savedOrganization));
    }

    @Override
    public List<UserResponse> getUsersByOrganizationId(UUID organizationId){
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + organizationId));

        return userMapper.toResponseList(organization.getUsers());
    }

    @Override
    public Page<OrganizationResponse> searchOrganizationByRegistryNumber(String registryNumber, Pageable pageable){
        String searchKey = null;
        if (registryNumber != null) {
            searchKey = registryNumber
                    .replaceAll("[^a-zA-Z0-9]", "")
                    .toLowerCase();
        }

        Page<Organization> organizationPage = organizationRepository
                .findByRegistryNumberContainingIgnoreCase(searchKey, pageable);

        return new PageImpl<>(
                organizationMapper.toResponseList(organizationPage.getContent()),
                pageable,
                organizationPage.getTotalElements()
        );
    }

    @Override
    public Page<OrganizationResponse> searchOrganizations(
            String name, Integer foundationYear, Integer companySize, Pageable pageable) {

        String normalizedName = null;
        if (name != null && !name.isBlank()) {
            normalizedName = name.toLowerCase().replaceAll("[^a-z0-9]", "");
        }

        Pageable safePageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<Organization> organizationPage =
                organizationRepository.searchOrganizations(normalizedName, foundationYear, companySize, safePageable);

        return new PageImpl<>(
                organizationMapper.toResponseList(organizationPage.getContent()),
                safePageable,
                organizationPage.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void deleteOrganization(UUID organizationId){
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + organizationId));
        organizationRepository.delete(organization);
    }
}
