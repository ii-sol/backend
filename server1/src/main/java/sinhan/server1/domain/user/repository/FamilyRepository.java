package sinhan.server1.domain.user.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sinhan.server1.domain.auth.dto.FamilyInfoInterface;
import sinhan.server1.domain.user.entity.Family;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    Optional<Family> findByParentsIdAndChildId(int id, int familyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Family f WHERE f.id = :id")
    void delete(@Param("id") int id);

    @Query("SELECT CASE " +
            "   WHEN f.parents.id = :id THEN f.child.id " +
            "   ELSE f.parents.id " +
            "END AS id, " +
            "CASE " +
            "   WHEN f.parents.id = :id THEN f.child.name " +
            "   ELSE f.parents.name " +
            "END AS name " +
            "FROM Family f " +
            "WHERE f.parents.id = :id OR f.child.id = :id " +
            "ORDER BY id")
    List<FamilyInfoInterface> findMyFamilyInfo(@Param("id") int id);
}
