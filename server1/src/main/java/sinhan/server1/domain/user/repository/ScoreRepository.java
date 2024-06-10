package sinhan.server1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.user.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
