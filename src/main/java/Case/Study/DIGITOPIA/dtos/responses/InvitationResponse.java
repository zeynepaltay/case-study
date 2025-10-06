package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class InvitationResponse {
    private UUID userId;
    private UUID organizationId;
    private String invitationMessage;
    private InvitationStatus invitationStatus;
}
