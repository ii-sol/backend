package sinhan.server1.domain.user.dto;

import java.sql.Date;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequest {

    private int id;
    private String phoneNum;
    private String name;
    private Date birthdate;
    private int profileId;
}
