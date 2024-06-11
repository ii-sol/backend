package sinhan.server1.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class JoinInfoSaveRequest {

    @JsonProperty(value = "phone_num")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNum;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "생일을 입력해주세요.")
    @Pattern(regexp = "\\d{8}", message = "생년원일은 8자리 숫자여야 합니다.")
    private Date birthdate;
    @JsonProperty(value = "account_info")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "비밀번호는 6자리 숫자여야 합니다.")
    private String accountInfo;
    @NotBlank(message = "역할을 입력해주세요.")
    private int role;
    @JsonProperty(value = "profile_id")
    private int profileId;
}
