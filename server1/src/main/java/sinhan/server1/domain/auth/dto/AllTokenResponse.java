package sinhan.server1.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllTokenResponse {

    private String accessToken;
    private String refreshToken;
}
