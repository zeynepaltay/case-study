package Case.Study.DIGITOPIA.repositories;

import Case.Study.DIGITOPIA.models.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    boolean existsByRegistryNumber(String registryNumber);
    Page<Organization> findByRegistryNumberContainingIgnoreCase(String registryNumber, Pageable pageable);
    @Query("""
        SELECT o FROM Organization o
        WHERE (:normalizedName IS NULL OR LOWER(o.normalizedName) LIKE LOWER(CONCAT('%', :normalizedName, '%')))
          AND (:foundationYear IS NULL OR o.foundationYear = :foundationYear)
          AND (:companySize IS NULL OR o.companySize = :companySize)
    """)
    Page<Organization> searchOrganizations(@Param("normalizedName") String normalizedName,
                                           @Param("foundationYear") Integer foundationYear,
                                           @Param("companySize") Integer companySize,
                                           Pageable pageable);
}

