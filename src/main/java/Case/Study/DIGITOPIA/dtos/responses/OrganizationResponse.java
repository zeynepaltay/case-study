package Case.Study.DIGITOPIA.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationResponse {
    private String name;
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private List<UUID> userId;
}
