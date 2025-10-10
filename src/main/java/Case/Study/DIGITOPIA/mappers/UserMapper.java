package Case.Study.DIGITOPIA.mappers;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {
    @Mapping(target = "organization", source = "organizationId")
    User toEntity(UserRequest request);

    @Mapping(source = "organization", target = "organizationId")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    default List<UUID> mapOrganizationsToOrganizationIds(List<Organization> organizations) {
        if (organizations == null || organizations.isEmpty()) {
            return Collections.emptyList();
        }
        return organizations.stream()
                .map(Organization::getID)
                .collect(Collectors.toList());
    }
}