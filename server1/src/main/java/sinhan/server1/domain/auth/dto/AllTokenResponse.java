package sinhan.server1.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllTokenResponse {

    @NotBlank(message = "refreshToken을 입력해주세요.")
    private String accessToken;
    @NotBlank(message = "refreshToken을 입력해주세요.")
    private String refreshToken;
}
