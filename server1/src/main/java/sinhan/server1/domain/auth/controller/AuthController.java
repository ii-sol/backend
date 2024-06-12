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
import sinhan.server1.domain.auth.dto.AllTokenResponse;
import sinhan.server1.domain.auth.dto.FamilyInfoInterface;
import sinhan.server1.domain.auth.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.auth.dto.LoginInfoFindRequest;
import sinhan.server1.domain.auth.service.AuthService;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;

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

        return user != null ? success("가입되었습니다.") : error("가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest, HttpServletResponse response) {

        UserFindOneResponse user = authService.login(loginInfoFindRequest);

        Map<String, List<FamilyInfoInterface>> familyInfo = new HashMap<>();
        log.info("userId={}", user.getId());
        familyInfo.put("familyInfo", authService.getFamilyInfo(user.getId()));
        familyInfo.get("familyInfo").forEach(info -> log.info("Family Info - ID: {}, Name: {}", info.getId(), info.getName()));

        AllTokenResponse allTokenResponse = new AllTokenResponse(jwtService.createAccessToken(user.getRole(), user.getId(), familyInfo), jwtService.createRefreshToken(user.getId()));

        response.setHeader("Authorization", "Bearer " + allTokenResponse.getAccessToken());
        response.setHeader("Refresh-Token", allTokenResponse.getRefreshToken());

        return success("로그인되었습니다.");
    }

    @PostMapping("/logout")
    public ApiUtils.ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        response.setHeader("Authorization", "");
        response.setHeader("Refresh-Token", "");

        return success("로그아웃되었습니다.");
    }
}
