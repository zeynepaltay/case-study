package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.mappers.OrganizationMapper;
import Case.Study.DIGITOPIA.mappers.UserMapper;
import Case.Study.DIGITOPIA.models.User;
import Case.Study.DIGITOPIA.repositories.UserRepository;
import Case.Study.DIGITOPIA.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;

    @Override
    public Optional<UserResponse> createUser(UserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("User with this email already exists");//TODO bu update de lazım olabilir
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return Optional.ofNullable(userMapper.toResponse(saved));
    }

    @Override
    public Optional<UserResponse> updateUser(UUID id, UserRequest request){
        User user = userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return Optional.ofNullable(userMapper.toResponse(saved));
    }

    @Override
    public Set<OrganizationResponse> getOrganizationsByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return organizationMapper.toResponseSet(user.getOrganization());
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
