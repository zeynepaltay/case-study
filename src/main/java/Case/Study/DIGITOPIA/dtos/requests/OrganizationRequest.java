package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.User;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationRequest {
    private String name;
    private String normalizedName;//??
    private String registeryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private Set<User> user = new HashSet<>();
}
