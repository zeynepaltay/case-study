package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrganizationResponse {
    private String name;
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private List<User> user;
}
