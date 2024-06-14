package sinhan.server1.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import sinhan.server1.domain.user.entity.User;

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
    @NotNull(message = "생일을 입력해주세요.")
    @Past(message = "생일은 현재 날짜보다 이전이어야 합니다.")
    private Date birthdate;
    @JsonProperty(value = "account_info")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "비밀번호는 6자리 숫자여야 합니다.")
    private String accountInfo;
    @NotNull(message = "역할을 입력해주세요.")
    @Min(value = 1, message = "역할은 1 또는 2입니다.")
    @Max(value = 2, message = "역할은 1 또는 2입니다.")
    private Integer role;
    @JsonProperty(value = "profile_id")
    private Integer profileId;

    public User convertToUser(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(accountInfo);
        return new User(phoneNum, name, birthdate, encodedPassword, role, profileId);
    }
}
