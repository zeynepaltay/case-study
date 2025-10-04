package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitationRequest {
    private Long userId;
    private Long organizationId;
    private String invitationMessage;
    private InvitationStatus invitationStatus;
}
