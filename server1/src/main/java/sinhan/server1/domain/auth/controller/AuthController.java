package sinhan.server1.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.auth.dto.AllTokenResponse;
import sinhan.server1.domain.auth.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.auth.service.AuthService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;

import static sinhan.server1.global.utils.ApiUtils.error;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;

    @PostMapping("/join")
    public ApiUtils.ApiResult join(@Valid @RequestBody JoinInfoSaveRequest joinInfoSaveRequest) {
//        authService.join(joinInfoSaveRequest);

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logIn")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest) {


//        AllTokenResponse allTokenResponse = new AllTokenResponse(
//                jwtService.getAccessToken(), jwtService.createRefreshToken()
//        );

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
