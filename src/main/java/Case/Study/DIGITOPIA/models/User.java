package Case.Study.DIGITOPIA.models;

import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "user")
public class User {
    @Id
    private UUID ID;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String fullName;
    private String normalizedName;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    @ManyToMany(mappedBy = "users")
    private Set<Organization> organization = new HashSet<>();
}
