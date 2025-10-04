package Case.Study.DIGITOPIA.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    private UUID ID;
    private String name;
    private String normalizedName;
    private String registeryNumber;
    private String email;
    private Integer companySize;
    private Integer foundationYear;
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    @ManyToMany
    @JoinTable(
            name = "organization_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private Set<User> user = new HashSet<>();
}
