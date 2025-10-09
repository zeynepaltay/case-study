package Case.Study.DIGITOPIA.controllers;

import Case.Study.DIGITOPIA.dtos.requests.InvitationRequest;
import Case.Study.DIGITOPIA.dtos.responses.InvitationResponse;
import Case.Study.DIGITOPIA.services.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping(value = "create-invitation")
    public ResponseEntity<Optional<InvitationResponse>> createInvitation(@Valid @RequestBody InvitationRequest request) {
        Optional<InvitationResponse> invitationResponse = invitationService.createInvitation(request);
        return ResponseEntity.ok(invitationResponse);
    }

    @GetMapping("/pending-check")
    public ResponseEntity<Boolean> checkPendingInvitation(@RequestParam UUID userId, @RequestParam UUID organizationId
    ) {
        boolean hasPending = invitationService.pendingInvitationCheck(userId, organizationId);
        return ResponseEntity.ok(hasPending);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable("id") UUID invitationId) {
        invitationService.deleteInvitation(invitationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "send-invitation")
    public ResponseEntity<Optional<InvitationResponse>> sendInvitation(@Valid @RequestBody InvitationRequest request){
        Optional<InvitationResponse> invitationResponse = invitationService.sendInvitation(request);
        return ResponseEntity.ok(invitationResponse);
    }

    @PutMapping("update-invitation")
    public ResponseEntity<Optional<InvitationResponse>> updateInvitation(@RequestParam UUID id,@Valid @RequestBody InvitationRequest request){
        Optional<InvitationResponse> invitationResponse = invitationService.updateInvitation(id, request);
        return ResponseEntity.ok(invitationResponse);
    }
}
