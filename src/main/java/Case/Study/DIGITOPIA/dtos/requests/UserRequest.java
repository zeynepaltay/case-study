package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private UserStatus userStatus;//bu silinmeli mi? kendi mi seçiyo yo bence silinmeli
    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Must contain only letters")
    private String fullName;
    @NotBlank(message = "Normalized name is required")
    @Pattern(regexp = "[a-z0-9]+$", message = "Must contain only letters and numbers")
    private String normalizedName; // bunu kendi mi yazıyo?
    private Role role;
    private Set<Organization> organization = new HashSet<>();
}
