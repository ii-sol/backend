package sinhan.server1.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.auth.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.auth.dto.JwtTokenResponse;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.auth.service.AuthService;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.global.security.JwtService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sinhan.server1.global.utils.ApiUtils.error;
import static sinhan.server1.global.utils.ApiUtils.success;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;

    @PostMapping("/join")
    public ApiUtils.ApiResult join(@Valid @RequestBody JoinInfoSaveRequest joinInfoSaveRequest) {
        UserFindOneResponse user = authService.join(joinInfoSaveRequest);

        return user != null
                ? success("가입되었습니다.")
                : error("가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest, HttpServletResponse httpServletResponse) throws AuthException {

        UserFindOneResponse user = authService.login(loginInfoFindRequest);
        List<FamilyInfoResponse> myFamilyInfo = authService.getFamilyInfo(user.getSerialNumber());
        setFamilyName(myFamilyInfo);

        Map<String, List<FamilyInfoResponse>> familyInfo = new HashMap<>();
        log.info("userSn={}", user.getSerialNumber());
        familyInfo.put("familyInfo", myFamilyInfo);
        familyInfo.get("familyInfo").forEach(info -> log.info("Family Info - SN: {}, Name: {}", info.getSn(), info.getName()));

        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.createAccessToken(user.getSerialNumber(), familyInfo), jwtService.createRefreshToken(user.getSerialNumber()));
        jwtService.sendJwtToken(httpServletResponse, jwtTokenResponse);

        return success("로그인되었습니다.");
    }

    private void setFamilyName(List<FamilyInfoResponse> myFamilyInfo) {
        // TODO: 부모 이름 가져오기 이벤트 등록 - 콜백
    }

    @PostMapping("/logout")
    public ApiUtils.ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

//        response.setHeader("Authorization", null);
//        response.setHeader("Refresh-Token", null);
        jwtService.sendJwtToken(response, new JwtTokenResponse());

        return success("로그아웃되었습니다.");
    }
}
