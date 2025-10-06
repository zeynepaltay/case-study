package Case.Study.DIGITOPIA.services;

import Case.Study.DIGITOPIA.dtos.requests.InvitationRequest;
import Case.Study.DIGITOPIA.dtos.responses.InvitationResponse;

import java.util.Optional;
import java.util.UUID;

public interface InvitationService {
    Optional<InvitationResponse> createInvitation(InvitationRequest request);
    boolean pendingInvitationCheck(UUID userId, UUID organizationId);
    void deleteInvitation(UUID invitationId);
    Optional<InvitationResponse> sendInvitation(InvitationRequest request);
}
