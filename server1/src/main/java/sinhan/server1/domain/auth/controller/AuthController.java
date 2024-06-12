package sinhan.server1.domain.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.auth.dto.*;
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
    private UserService userService;
    private JwtService jwtService;

    @PostMapping("/join")
    public ApiUtils.ApiResult join(@Valid @RequestBody JoinInfoSaveRequest joinInfoSaveRequest) {
//        authService.join(joinInfoSaveRequest);

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest, HttpServletResponse response) {

        UserFindOneResponse user = authService.login(loginInfoFindRequest);

        Map<String, List<FamilyInfoInterface>> familyInfo = new HashMap<>();
        log.info("userId={}", user.getId());
        familyInfo.put("familyInfo", authService.getFamily(user.getId()));
        familyInfo.get("familyInfo").forEach(info -> log.info("Family Info - ID: {}, Name: {}", info.getId(), info.getName()));

        AllTokenResponse allTokenResponse = new AllTokenResponse(
                jwtService.createAccessToken(user.getRole(), user.getId(), familyInfo),
                jwtService.createRefreshToken(user.getId())
        );

        response.setHeader("Authorization", "Bearer " + allTokenResponse.getAccessToken());
        response.setHeader("Refresh-Token", allTokenResponse.getRefreshToken());

        return success("로그인되었습니다.");
    }
}
