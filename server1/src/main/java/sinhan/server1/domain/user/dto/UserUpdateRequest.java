package sinhan.server1.domain.user.dto;

import java.sql.Date;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {

    private long serialNum;
    private final String phoneNum;
    private final String name;
    private final Date birthdate;
    private final int profileId;
}
