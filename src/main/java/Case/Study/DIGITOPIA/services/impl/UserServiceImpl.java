package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.mappers.OrganizationMapper;
import Case.Study.DIGITOPIA.mappers.UserMapper;
import Case.Study.DIGITOPIA.models.User;
import Case.Study.DIGITOPIA.repositories.UserRepository;
import Case.Study.DIGITOPIA.services.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    public Set<OrganizationResponse> getOrganizationsByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return organizationMapper.toResponseSet(user.getOrganization());
    }

    @Override
    public Page<UserResponse> searchUserByNormalizedName(String normalizedName, Pageable pageable) {
        String searchKey = normalizedName != null ? normalizedName.toLowerCase() : null;

        Page<User> usersPage = userRepository.findByNormalizedNameContainingIgnoreCase(searchKey, pageable);

        return new PageImpl<>(
                userMapper.toResponseList(usersPage.getContent()),
                pageable,
                usersPage.getTotalElements()
        );
    }

    @Override
    public Optional<UserResponse> searchByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        return Optional.ofNullable(userMapper.toResponse(user));
    }
}
