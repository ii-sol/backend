package sinhan.server1.domain.mission.dto;

import lombok.Getter;
import sinhan.server1.domain.mission.entity.Mission;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MissionFindManyResponse {

    private final List<MissionFindOneResponse> missions;

    public MissionFindManyResponse(List<Mission> missions) {
        this.missions = new ArrayList<>();

        for (Mission mission : missions) {
            this.missions.add(mission.convertToMissionFindOneResponse());
        }
    }
}
