package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.mappers.OrganizationMapper;
import Case.Study.DIGITOPIA.mappers.UserMapper;
import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.User;
import Case.Study.DIGITOPIA.repositories.OrganizationRepository;
import Case.Study.DIGITOPIA.repositories.UserRepository;
import Case.Study.DIGITOPIA.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;
    private final OrganizationRepository organizationRepository;

    @Override
    public Optional<UserResponse> createUser(UserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("User with this email already exists");
        }
        User user = userMapper.toEntity(request);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);

        return Optional.ofNullable(userMapper.toResponse(saved));
    }

    @Override
    @Transactional
    public Optional<UserResponse> updateUser(UUID id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        List<Organization> organizations = organizationRepository.findAllById(request.getOrganizationId());
        String newEmail = request.getEmail();

        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (!newEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new IllegalStateException("Email already exists");
        }

        List<Organization> organization = organizationRepository.findAllById(request.getOrganizationId());

        if (organizations.size() != request.getOrganizationId().size()) {
            throw new IllegalArgumentException("One or more organization IDs are invalid");
        }

        user.setEmail(newEmail);
        user.setFullName(request.getFullName());
        user.setNormalizedName(request.getFullName().toLowerCase().replaceAll("\\s+", ""));
        user.setUserStatus(request.getUserStatus());
        user.setRole(request.getRole());
        user.setOrganization(organization);
        user.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        return Optional.ofNullable(userMapper.toResponse(saved));
    }


    @Override
    @Transactional
    public List<OrganizationResponse> getOrganizationsByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        List<Organization> organizations = user.getOrganization();

        if (organizations == null || organizations.isEmpty()) {
            return Collections.emptyList();
        }

        return organizations.stream()
                .map(org -> {
                    OrganizationResponse response = organizationMapper.toResponse(org);

                    List<UUID> allUserIdsInOrg = org.getUsers().stream()
                            .map(User::getID)
                            .collect(Collectors.toList());

                    response.setUserId(allUserIdsInOrg);

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> searchUserByNormalizedName(String normalizedName, Pageable pageable) {

        String searchKey = null;
        if (normalizedName != null) {
            searchKey = normalizedName
                    .toLowerCase()
                    .replaceAll("[^\\p{ASCII}]", "")
                    .replaceAll("[^a-z0-9]", "");
        }

        Page<User> usersPage = userRepository.findByNormalizedNameContainingIgnoreCase(searchKey, pageable);

        return new PageImpl<>(
                userMapper.toResponseList(usersPage.getContent()),
                pageable,
                usersPage.getTotalElements()
        );
    }

    @Override
    public Optional<UserResponse> searchByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        return Optional.ofNullable(userMapper.toResponse(user));
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId){
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
