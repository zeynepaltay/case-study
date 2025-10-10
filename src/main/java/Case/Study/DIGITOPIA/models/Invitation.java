package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;
    @Column(name = "invitation_message", nullable = false, length = 300)
    private String invitationMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;
    @LastModifiedBy
    private UUID updatedBy;
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
}
