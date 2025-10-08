package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String fullName;
    private String normalizedName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    @ManyToMany(mappedBy = "users")
    private Set<Organization> organization = new HashSet<>();
}
