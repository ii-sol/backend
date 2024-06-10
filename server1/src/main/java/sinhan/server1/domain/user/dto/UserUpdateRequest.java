package sinhan.server1.domain.user.dto;

import lombok.*;

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
