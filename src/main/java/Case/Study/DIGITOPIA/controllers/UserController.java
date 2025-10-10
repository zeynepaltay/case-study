package Case.Study.DIGITOPIA.controllers;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "create-user")
    public ResponseEntity<Optional<UserResponse>> createUser(@Valid @RequestBody UserRequest request){
        Optional<UserResponse> userResponse = userService.createUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(value = "update-user")
    public ResponseEntity<Optional<UserResponse>> updateUser(@RequestParam UUID id,@Valid @RequestBody UserRequest request){
        Optional<UserResponse> userResponse = userService.updateUser(id, request);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value = "/organization-lists")
    public ResponseEntity<List<OrganizationResponse>> getOrganizationsByUserId(@RequestParam UUID userId) {
        List<OrganizationResponse> organizationResponse = userService.getOrganizationsByUserId(userId);
        return ResponseEntity.ok(organizationResponse);
    }

    @GetMapping(value = "search-normalized-name")
    public ResponseEntity<Page<UserResponse>> searchUserByNormalizedName(@RequestParam String normalizedName, @ParameterObject Pageable pageable){
        Page<UserResponse> userResponse = userService.searchUserByNormalizedName(normalizedName, pageable);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value ="/search-email")
    public ResponseEntity<Optional<UserResponse>> searchByEmail(@RequestParam String email){
        Optional<UserResponse> userResponse = userService.searchByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
