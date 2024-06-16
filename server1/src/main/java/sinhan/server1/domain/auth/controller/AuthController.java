package sinhan.server1.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.auth.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.auth.dto.JwtTokenResponse;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.auth.service.AuthService;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.global.security.JwtService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;

import static sinhan.server1.global.utils.ApiUtils.error;
import static sinhan.server1.global.utils.ApiUtils.success;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;

    @GetMapping("/main")
    public ApiUtils.ApiResult main() {
        return success("초기 화면");
    }

    @PostMapping("/join")
    public ApiUtils.ApiResult join(@Valid @RequestBody JoinInfoSaveRequest joinInfoSaveRequest, HttpServletResponse response) {
        UserFindOneResponse user = authService.join(joinInfoSaveRequest);

        if (user != null) {
            return success("가입되었습니다.");
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return error("가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest, HttpServletResponse response) throws AuthException, JsonProcessingException {
        try {
            UserFindOneResponse user = authService.login(loginInfoFindRequest);
            List<FamilyInfoResponse> myFamilyInfo = authService.getFamilyInfo(user.getSerialNumber());
            setFamilyName(myFamilyInfo);

            myFamilyInfo.forEach(info -> log.info("Family Info - SN: {}, Name: {}", info.getSn(), info.getName()));

            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.createAccessToken(user.getSerialNumber(), myFamilyInfo), jwtService.createRefreshToken(user.getSerialNumber()));
            jwtService.sendJwtToken(response, jwtTokenResponse);

            return success("로그인되었습니다.");
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return error("로그인에 실패하였습니다. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void setFamilyName(List<FamilyInfoResponse> myFamilyInfo) {
        // TODO: 부모 이름 가져오기 이벤트 등록 - 콜백
    }

    @PostMapping("/logout")
    public ApiUtils.ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        return success(""); // main으로 redirection
    }

    @PostMapping("/token")
    public ApiUtils.ApiResult refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.getRefreshToken();
        if (refreshToken == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return error("Refresh-Token을 찾지 못했습니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            long sn = jwtService.getUserInfo(refreshToken).getSn();
            List<FamilyInfoResponse> myFamilyInfo = authService.getFamilyInfo(sn);
            setFamilyName(myFamilyInfo);

            String newAccessToken = jwtService.createAccessToken(sn, myFamilyInfo);
            jwtService.sendAccessToken(response, newAccessToken);
            return success("Authorization이 새로 발급되었습니다.");
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return error("Refresh-Token 검증에 실패하였습니다. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
