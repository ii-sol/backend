package sinhan.server1.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class UserFindRequest {

    @Pattern(regexp = "^(\\d{3})-(\\d{3,4})-(\\d{4})$", message = "전화번호 형식이 맞지 않습니다.")
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNum;
    @Pattern(regexp = "^\\d{6}$", message = "비밀번호는 6자리 숫자입니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String accountInfo;

}
