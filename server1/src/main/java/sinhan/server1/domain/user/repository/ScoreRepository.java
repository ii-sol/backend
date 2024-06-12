package sinhan.server1.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.user.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    Optional<Score> findByChildId(int childId);
}
