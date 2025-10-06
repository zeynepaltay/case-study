package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationRequest {
    @NotBlank(message = "Organization name can not be left blank")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Must contain only letters and numbers")
    private String name;
    @NotBlank(message = "Normalized name can not be left blank")
    @Pattern(regexp = "[a-z0-9]+$", message = "Must contain only letters and numbers")
    private String normalizedName;// TODO bunu silcem full nameden kendim üretip işliycem sonra
    @NotBlank(message = "Registry number can not be left blank")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Must contain only letters and numbers")
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private Set<User> user;
}
