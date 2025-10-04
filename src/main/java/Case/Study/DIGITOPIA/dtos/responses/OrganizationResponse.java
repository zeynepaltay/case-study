package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrganizationResponse {
    private String name;
    private String normalizedName;//???
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private Date createdAt;// kalmalı mı?
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private Set<User> user = new HashSet<>();
}
