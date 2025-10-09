package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.requests.InvitationRequest;
import Case.Study.DIGITOPIA.dtos.responses.InvitationResponse;
import Case.Study.DIGITOPIA.mappers.InvitationMapper;
import Case.Study.DIGITOPIA.models.Invitation;
import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import Case.Study.DIGITOPIA.repositories.InvitationRepository;
import Case.Study.DIGITOPIA.services.InvitationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    @Override
    public Optional<InvitationResponse> createInvitation(InvitationRequest request){
        Invitation invitation = invitationMapper.toEntity(request);
        // TODO throw error var mı
        invitation.setCreatedAt(LocalDateTime.now());
        invitation.setUpdatedAt(LocalDateTime.now());
        Invitation saved = invitationRepository.save(invitation);
        return Optional.of(invitationMapper.toResponse(saved));
    }

    @Override
    public Optional<InvitationResponse> updateInvitation(UUID id, InvitationRequest request) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitation with id " + id + " not found"));

        if (request.getInvitationStatus() == InvitationStatus.PENDING
                && invitation.getInvitationStatus() != InvitationStatus.EXPIRED
                && invitation.getInvitationStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("User can only be reinvited if the previous invitation is expired.");
        }

        if (request.getInvitationStatus() == InvitationStatus.PENDING) {
            boolean existsPending = invitationRepository.pendingInvitationCheck(
                    invitation.getUserId(),
                    invitation.getOrganizationId(),
                    InvitationStatus.PENDING
            );

            if (existsPending && invitation.getInvitationStatus() != InvitationStatus.PENDING) {
                throw new IllegalStateException("Only one pending invitation can exist per user per organization.");
            }
        }

        invitation.setInvitationMessage(request.getInvitationMessage());
        invitation.setInvitationStatus(request.getInvitationStatus());
        invitation.setUpdatedAt(LocalDateTime.now());
        invitation.setUpdatedBy(request.getUserId());

        Invitation saved = invitationRepository.save(invitation);
        return Optional.of(invitationMapper.toResponse(saved));
    }//TODO düzgün mü

    @Override
    @Transactional
    public Optional<InvitationResponse> sendInvitation(InvitationRequest request) {
        UUID userId = request.getUserId();
        UUID organizationId = request.getOrganizationId();

        boolean existingOpt = invitationRepository.pendingInvitationCheck(userId, organizationId, InvitationStatus.PENDING);

        if (existingOpt) {
            throw new EntityExistsException("Invitation with id " + request.getUserId() + " already exists");
        }

        Invitation invitation = invitationMapper.toEntity(request);
        invitation.setInvitationStatus(InvitationStatus.PENDING);
        invitation.setCreatedAt(LocalDateTime.now());
        Invitation saved = invitationRepository.save(invitation);
        return Optional.of(invitationMapper.toResponse(saved));
    }

    @Override
    public boolean pendingInvitationCheck(UUID userId, UUID organizationId){
         return invitationRepository.pendingInvitationCheck(userId, organizationId, InvitationStatus.PENDING);
    }

    @Override
    @Transactional
    public void deleteInvitation(UUID invitationId){
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation not found with id " + invitationId));
        invitationRepository.delete(invitation);
    }
}
