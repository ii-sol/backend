package sinhan.server1.domain.user.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sinhan.server1.domain.user.entity.Family;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    Optional<Family> findByParentsIdAndChildId(int id, int familyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Family f WHERE f.id = :id")
    void delete(@Param("id") int id);
}
