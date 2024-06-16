package sinhan.server1.domain.mission.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.mission.dto.MissionFindManyResponse;
import sinhan.server1.domain.mission.entity.Mission;
import sinhan.server1.domain.mission.repository.MissionRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MissionService {

    private MissionRepository missionRepository;

    public MissionFindManyResponse getMissions(long sn, int status) {
        List<Mission> missions = missionRepository.findByChildSerialNumAndStatus(sn, status);

        return new MissionFindManyResponse(missions);
    }
}
