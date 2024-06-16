package sinhan.server1.domain.mission.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.mission.entity.Mission;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.entity.User;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MissionFindOneResponse {

    private int id;
    @JsonProperty(value = "parents_sn")
    private long parentsSn;
    @JsonProperty(value = "child_sn")
    private User child;
    private String content;
    private int price;
    @JsonProperty(value = "create_date")
    private Timestamp createDate;
    @JsonProperty(value = "due_date")
    private Timestamp dueDate;
    private int status;

    public MissionFindOneResponse from(Mission mission) {
        return MissionFindOneResponse.builder()
                .id(mission.getId())
                .parentsSn(mission.getParentsSn())
                .child(mission.getChild())
                .content(mission.getContent())
                .createDate(mission.getCreateDate())
                .dueDate(mission.getDueDate())
                .status(mission.getStatus())
                .build();
    }
}
