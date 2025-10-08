package Case.Study.DIGITOPIA.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    private String name;
    private String normalizedName;
    @Column(name = "organization_id", nullable = false, unique = true, length = 20)// Aynı classNumber ile ikinci kayıt eklenmeye çalışılırsa DB hata fırlatır
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    @ManyToMany
    @JoinTable(
            name = "organization_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private Set<User> user;
}
