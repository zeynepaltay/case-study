package Case.Study.DIGITOPIA.repositories;

import Case.Study.DIGITOPIA.models.Invitation;
import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    List<Invitation> findByCreatedAtBeforeAndInvitationStatusNot(LocalDateTime date, InvitationStatus invitationStatus);

    @Query("""
    SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END
    FROM Invitation i
    WHERE i.userId = :userId
      AND i.organizationId = :organizationId
      AND i.invitationStatus = :status
    """)
    boolean pendingInvitationCheck(@Param("userId") UUID userId,
                                   @Param("organizationId") UUID organizationId,
                                   @Param("invitationStatus") InvitationStatus invitationStatus);
    Optional<Invitation> findByUserIdAndOrganizationId(UUID userId, UUID organizationId);
}
