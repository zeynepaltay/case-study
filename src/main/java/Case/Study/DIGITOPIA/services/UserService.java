package Case.Study.DIGITOPIA.services;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    Set<OrganizationResponse> getOrganizationsByUserId(UUID userId);
    Page<UserResponse> searchUserByNormalizedName(String normalizedName, Pageable pageable);
    Optional<UserResponse> searchByEmail(String email);
    Optional<UserResponse> createUser(UserRequest request);
    Optional<UserResponse> updateUser(UUID id, UserRequest request);
    void deleteUser(UUID userId);
}
