package sinhan.server1.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.mission.entity.Mission;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Integer> {

    List<Mission> findByChildSerialNumAndStatus(long sn, int status);
}
