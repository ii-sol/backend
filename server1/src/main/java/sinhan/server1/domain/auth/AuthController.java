package sinhan.server1.domain.auth;

import static sinhan.server1.global.utils.ApiUtils.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("/logIn")
    public ApiUtils.ApiResult login(@RequestBody LoginInfoFindRequest loginInfoFindRequest){

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
