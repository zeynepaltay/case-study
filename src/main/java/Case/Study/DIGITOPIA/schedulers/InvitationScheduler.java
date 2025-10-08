package Case.Study.DIGITOPIA.schedulers;

import Case.Study.DIGITOPIA.models.Invitation;
import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import Case.Study.DIGITOPIA.repositories.InvitationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class InvitationScheduler {
    private final InvitationRepository invitationRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void scheduleInvitation() {//TODO bunu çağırmam gerekiyo mu bak
    LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
    List<Invitation> oldInvitations = invitationRepository.findByCreatedAtBeforeAndInvitationStatusNot(sevenDaysAgo, InvitationStatus.EXPIRED);
    oldInvitations.forEach(invitation -> invitation.setInvitationStatus(InvitationStatus.EXPIRED));
    invitationRepository.saveAll(oldInvitations);
    System.out.println(oldInvitations.size() + " invitation(s) updated to EXPIRED at " + LocalDateTime.now());
    }
}
