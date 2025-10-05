package Case.Study.DIGITOPIA.repositories;

import Case.Study.DIGITOPIA.models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
}
