package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.user.entity.User;

@Builder
@AllArgsConstructor
@Getter
public class FamilyFindOneResponse {

    private int id;
    @JsonProperty(value = "parents_id")
    private User parents;
    @JsonProperty(value = "child_id")
    private User child;
}
