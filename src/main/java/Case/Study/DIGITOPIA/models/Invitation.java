package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
