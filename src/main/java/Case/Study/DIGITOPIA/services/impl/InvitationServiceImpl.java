package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.dtos.requests.InvitationRequest;
import Case.Study.DIGITOPIA.dtos.responses.InvitationResponse;
import Case.Study.DIGITOPIA.mappers.InvitationMapper;
import Case.Study.DIGITOPIA.mappers.UserMapper;
import Case.Study.DIGITOPIA.models.Invitation;
import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import Case.Study.DIGITOPIA.repositories.InvitationRepository;
import Case.Study.DIGITOPIA.services.InvitationService;
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
        Invitation saved = invitationRepository.save(invitation);
        return Optional.of(invitationMapper.toResponse(saved));
    }

    @Override
    public Optional<InvitationResponse> updateInvitation(UUID id, InvitationRequest request){
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitation with id " + id + " not found"));
        invitationMapper.toEntity(request);
        Invitation saved = invitationRepository.save(invitation);
        return Optional.of(invitationMapper.toResponse(saved));
    }
    /// todo statu değişimlerini burda yaomam lazım reject vs tam kuramadım yapıyı

    @Override
    @Transactional//TODO bunun doğruluğunu bi kontrol et çok karıştı
    public Optional<InvitationResponse> sendInvitation(InvitationRequest request) {
        UUID userId = request.getUserId();
        UUID organizationId = request.getOrganizationId();

        Optional<Invitation> existingOpt = invitationRepository.findByUserIdAndOrganizationId(userId, organizationId);

        if (existingOpt.isPresent()) {
            Invitation existing = existingOpt.get();

            if (existing.getInvitationStatus() == InvitationStatus.PENDING) {
                throw new IllegalStateException("There is already a pending invitation for this user and organization.");
            }

            if (existing.getInvitationStatus() == InvitationStatus.EXPIRED) {
                existing.setInvitationStatus(InvitationStatus.PENDING);
                existing.setCreatedAt(LocalDateTime.now());
                Invitation saved = invitationRepository.save(existing);
                return Optional.of(invitationMapper.toResponse(saved));
            }
            throw new IllegalStateException("Cannot resend invitation when status is: " + existing.getInvitationStatus());
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
