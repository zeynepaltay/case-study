package Case.Study.DIGITOPIA.controllers;

import Case.Study.DIGITOPIA.dtos.requests.OrganizationRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.services.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping(value = "create-organization")
    public ResponseEntity<Optional<OrganizationResponse>> createOrganization(@Valid @RequestBody OrganizationRequest request){
        Optional<OrganizationResponse> organizationResponse = organizationService.createOrganization(request);
        return ResponseEntity.ok(organizationResponse);
    }

    @PutMapping(value = "update-organization")
    public ResponseEntity<Optional<OrganizationResponse>> updateOrganization(@RequestParam UUID organizationId, @Valid @RequestBody OrganizationRequest request){
        Optional<OrganizationResponse> organizationResponse = organizationService.updateOrganization(organizationId, request);
        return ResponseEntity.ok(organizationResponse);
    }

    @GetMapping(value = "/users-lists")
    public ResponseEntity<List<UserResponse>> getUsersByOrganizationId(@RequestParam  UUID organizationId) {
        List<UserResponse> userResponse = organizationService.getUsersByOrganizationId(organizationId);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrganizationResponse> deleteOrganization(@PathVariable("id") UUID organizationId) {
        organizationService.deleteOrganization(organizationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search-organization")
    public ResponseEntity<Page<OrganizationResponse>> searchOrganizationByRegistryNumber(@RequestParam String registryNumber, Pageable pageable){
        Page<OrganizationResponse> result = organizationService.searchOrganizationByRegistryNumber(registryNumber, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search-multiple")
    public ResponseEntity<Page<OrganizationResponse>> searchOrganizations
            (@RequestParam(required = false) String name,//TODO bunlar required false olmalı mı bak
            @RequestParam(required = false) Integer foundationYear,
            @RequestParam(required = false) Integer companySize,
            Pageable pageable) {

        Page<OrganizationResponse> result =
                organizationService.searchOrganizations(name, foundationYear, companySize, pageable);
        return ResponseEntity.ok(result);
    }

}
