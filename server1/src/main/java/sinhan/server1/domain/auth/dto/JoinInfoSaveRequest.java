package sinhan.server1.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class JoinInfoSaveRequest {

    @JsonProperty(value = "phone_num")
    private String phoneNum;
    private String name;
    private Date birthdate;
    @JsonProperty(value = "account_info")
    private String accountInfo;
    private int role;
    @JsonProperty(value = "profile_id")
    private int profileId;
}
