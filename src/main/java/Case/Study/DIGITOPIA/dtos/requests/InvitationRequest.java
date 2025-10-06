package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitationRequest {
    private UUID userId;
    private UUID organizationId;
    private String invitationMessage;
    private InvitationStatus invitationStatus;
}
