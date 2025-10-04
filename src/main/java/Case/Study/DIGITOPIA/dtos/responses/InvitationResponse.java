package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class InvitationResponse {
    private Long userId;
    private Long organizationId;
    private String invitationMessage;
    private Date createdAt;//??
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private InvitationStatus invitationStatus;
}
