package Case.Study.DIGITOPIA.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ID;
    @Column(nullable = false)
    private String name;
    private String normalizedName;
    @Column(name = "organization_id", nullable = false, unique = true, length = 20)// Aynı classNumber ile ikinci kayıt eklenmeye çalışılırsa DB hata fırlatır
    private String registryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;
    @LastModifiedBy
    private UUID updatedBy;
    @ManyToMany
    @JoinTable(
            name = "organization_user",
            joinColumns = @JoinColumn(name = "organization_id"), // Organization FK
            inverseJoinColumns = @JoinColumn(name = "user_id") // User FK
    )
    private Set<User> users = new HashSet<>();
}
