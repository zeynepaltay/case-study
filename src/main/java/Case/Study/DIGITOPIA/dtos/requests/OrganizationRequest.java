package Case.Study.DIGITOPIA.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationRequest {
    @NotBlank(message = "Organization name can not be left blank")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Must contain only letters and numbers")
    private String name;
    // TODO bunu silcem full nameden kendim üretip işliycem sonra
    @NotBlank(message = "Registry number can not be left blank")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Must contain only letters and numbers")
    private String registryNumber;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private List<UUID> userId;
}
