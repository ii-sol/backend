package sinhan.server1.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.user.entity.User;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserFindOneResponse {

    private int id;
    @JsonProperty(value = "serial_number")
    private Long serialNumber;
    @JsonProperty(value = "phone_num")
    private String phoneNum;
    private String name;
    private Date birthdate;
    @JsonProperty(value = "account_info")
    private String accountInfo;
    @JsonProperty(value = "profile_id")
    private int profileId;

    public static UserFindOneResponse from(User user) {
        return UserFindOneResponse.builder()
                .id(user.getId())
                .serialNumber(user.getSerialNumber())
                .phoneNum(user.getPhoneNum())
                .name(user.getName())
                .birthdate(user.getBirthDate())
                .accountInfo(user.getAccountInfo())
                .profileId(user.getProfileId())
                .build();
    }

}
