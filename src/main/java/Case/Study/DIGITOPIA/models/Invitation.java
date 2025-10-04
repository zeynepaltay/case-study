package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    private UUID ID;
    private Long userId;
    private Long organizationId;
    private String invitationMessage;
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
}
