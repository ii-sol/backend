package sinhan.server1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.user.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
}
