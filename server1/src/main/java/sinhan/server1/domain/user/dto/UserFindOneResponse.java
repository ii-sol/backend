package sinhan.server1.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class UserFindOneResponse {

    private int id;
    private String phoneNum;
    private String name;
    private String birthdate;
    private String accountInfo;
    private int role;
    private int profileId;

}
