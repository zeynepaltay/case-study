package Case.Study.DIGITOPIA.mappers;

import Case.Study.DIGITOPIA.dtos.requests.OrganizationRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.models.Organization;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toEntity(OrganizationRequest request);
    OrganizationResponse toResponse(Organization organizations);
    List<OrganizationResponse> toResponseList(List<Organization> organizations);
}
