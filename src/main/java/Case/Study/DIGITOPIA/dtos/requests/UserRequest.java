package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String email;
    private UserStatus userStatus; //bu silinmeli mi? kendi mi seçiyo yo bence silinmeli
    private String fullName;
    private String normalizedName; // bunu kendi mi yazıyo?
    private Role role;
    private Set<Organization> organization = new HashSet<>();
}
