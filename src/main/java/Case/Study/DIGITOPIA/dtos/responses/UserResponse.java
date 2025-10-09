package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String email;
    private UserStatus userStatus;
    private String fullName;
    private Role role;
    private List<Organization> organization;
}
