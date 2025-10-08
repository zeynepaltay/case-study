package Case.Study.DIGITOPIA.mappers;

import Case.Study.DIGITOPIA.dtos.requests.UserRequest;
import Case.Study.DIGITOPIA.dtos.responses.UserResponse;
import Case.Study.DIGITOPIA.models.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {
    User toEntity(UserRequest request);
    UserResponse toResponse(User users);
    List<UserResponse> toResponseList(List<User> users);
    Set<UserResponse> toResponseSet(Set<User> users);//TODO bu ve bi üstteki aynı şey???
}
