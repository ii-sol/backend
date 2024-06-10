package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequest {

    private int id;
    private String phoneNum;
    private String name;
    private String birthdate;
    private int profileId;
}
