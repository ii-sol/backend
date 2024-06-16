package sinhan.server1.domain.mission.service;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.mission.entity.Mission;

public interface MissionService extends JpaRepository<Mission, Integer> {
}
