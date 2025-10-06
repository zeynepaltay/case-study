package Case.Study.DIGITOPIA.controllers;

import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/organization-lists")
    public ResponseEntity<Set<OrganizationResponse>> getOrganizationsByUserId(@Valid @RequestParam UUID userId) {
        Set<OrganizationResponse> organizationResponse = userService.getOrganizationsByUserId(userId);
        return ResponseEntity.ok(organizationResponse);
    }

    @GetMapping(value = "search-normalized-name")
    public ResponseEntity<Page<UserResponse>> searchUserByNormalizedName(@Valid @RequestParam String normalizedName, Pageable pageable){
        Page<UserResponse> userResponse = userService.searchUserByNormalizedName(normalizedName, pageable);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value ="/search-email")
    public ResponseEntity<Optional<UserResponse>> searchByEmail(String email){
        Optional<UserResponse> userResponse = userService.searchByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

}
