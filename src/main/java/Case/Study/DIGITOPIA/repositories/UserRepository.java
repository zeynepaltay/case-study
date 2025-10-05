package Case.Study.DIGITOPIA.repositories;

import Case.Study.DIGITOPIA.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByNormalizedNameContainingIgnoreCase(String normalizedName, Pageable pageable);
    Optional<User> findByEmail(String email);
}
