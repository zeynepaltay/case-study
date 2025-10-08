package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    @Column(name = "user_id", nullable = false, unique = true, length = 20)//TODO buna bak
    private Long userId;
    @Column(name = "organization_id", nullable = false, unique = true, length = 20)
    private Long organizationId;
    @Column(name = "invitation_message", nullable = false, unique = true, length = 100)
    private String invitationMessage;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private UUID createdBy;
    @Column
    private UUID updatedBy;
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
}
