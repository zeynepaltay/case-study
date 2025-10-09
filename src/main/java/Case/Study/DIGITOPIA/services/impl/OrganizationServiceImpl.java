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
    public Optional<OrganizationResponse> createOrganization(OrganizationRequest request){
        if(organizationRepository.existsByRegistryNumber(request.getRegistryNumber())){
            throw new EntityExistsException("That registry number already exists");
        }

        Organization organization = organizationMapper.toEntity(request);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());
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

        List<User> user = userRepository.findAllById(request.getUserId());

        organization.setName(request.getName());
        organization.setUpdatedAt(LocalDateTime.now());
        organization.setUsers(user);
        organization.setCompanySize(request.getCompanySize());
        organization.setFoundationYear(request.getFoundationYear());
        organization.setRegistryNumber(request.getRegistryNumber());
        organization.setEmail(request.getEmail()); // email unique olmalı, istersen ekstra kontrol ekle
        organization.setUpdatedAt(LocalDateTime.now());

        Organization saved = organizationRepository.save(organization);
        return Optional.ofNullable(organizationMapper.toResponse(saved));
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
                    .toLowerCase();//TODO bu kalmalı mı?
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
    public Page<OrganizationResponse> searchOrganizations(String name, Integer foundationYear, Integer companySize, Pageable pageable){

        String normalizedName = null;
        if (name != null) {
            normalizedName = name
                    .toLowerCase()                  //TODO kalsın mı ya lowercase
                    .replaceAll("[^a-z0-9]", "");   // sadece alphanumeric (ASCII only)
        }

        Page<Organization> organizationPage =
                organizationRepository.searchOrganizations(normalizedName, foundationYear, companySize, pageable);

        return new PageImpl<>(
                organizationMapper.toResponseList(organizationPage.getContent()),
                pageable,
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
