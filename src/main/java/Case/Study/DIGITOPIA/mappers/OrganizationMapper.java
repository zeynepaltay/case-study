package Case.Study.DIGITOPIA.mappers;

import Case.Study.DIGITOPIA.dtos.requests.OrganizationRequest;
import Case.Study.DIGITOPIA.dtos.responses.OrganizationResponse;
import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toEntity(OrganizationRequest request);

    @Mapping(source = "users", target = "userId")
    OrganizationResponse toResponse(Organization organization);

    List<OrganizationResponse> toResponseList(List<Organization> organizations);

    default List<UUID> mapUsersToUserIds(List<User> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(User::getID)
                .collect(Collectors.toList());
    }

    default Organization toEntity(UUID id) {
        if (id == null) return null;
        Organization organization = new Organization();
        organization.setID(id);
        return organization;
    }

    default UUID toId(Organization organization) {
        return organization != null ? organization.getID() : null;
    }
}
