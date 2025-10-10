package Case.Study.DIGITOPIA.dtos.requests;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Invitation message cannot be blank")
    @Size(max = 300, message = "Invitation message must be at most 300 characters long")
    private String invitationMessage;
    private InvitationStatus invitationStatus;
}
