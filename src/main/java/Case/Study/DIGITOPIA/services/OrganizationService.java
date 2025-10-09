package Case.Study.DIGITOPIA.services;


import Case.Study.DIGITOPIA.dtos.requests.OrganizationRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationService {
    Optional<OrganizationResponse> createOrganization(OrganizationRequest request);
    Optional<OrganizationResponse> updateOrganization(UUID organizationId, OrganizationRequest request);
    void deleteOrganization(UUID organizationId);
    List<UserResponse> getUsersByOrganizationId(UUID organizationId);
    Page<OrganizationResponse> searchOrganizationByRegistryNumber(String registryNumber, Pageable pageable);
    Page<OrganizationResponse> searchOrganizations(String name, Integer year, Integer companySize, Pageable pageable);
}
