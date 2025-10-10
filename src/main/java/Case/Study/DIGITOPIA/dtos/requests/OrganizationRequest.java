package Case.Study.DIGITOPIA.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Registry number can not be left blank")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Must contain only letters and numbers")
    @Column(name = "registry_number", unique = true)
    private String registryNumber;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Company size cannot be null")
    @Positive(message = "Company size must be a positive number")
    private Integer companySize;
    @NotNull(message = "Foundation year cannot be null")
    @Min(value = 1900, message = "Foundation year must be after 1900")
    @Max(value = 2025, message = "Foundation year cannot be in the future")
    private Integer foundationYear;
    private List<UUID> userId;
}
