package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String email;
    private UserStatus userStatus; //bu silinmeli mi? kendi mi seçiyo yo bence silinmeli
    private String fullName;
    private Role role;
    private Set<Organization> organization;
}
