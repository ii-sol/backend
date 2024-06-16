package sinhan.server1.domain.mission.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.auth.dto.UserInfoResponse;
import sinhan.server1.domain.mission.service.MissionService;
import sinhan.server1.global.security.JwtService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.exception.AuthException;

import static sinhan.server1.global.utils.ApiUtils.success;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private MissionService missionService;
    private JwtService jwtService;

    @GetMapping("/{status}")
    public ApiUtils.ApiResult getMissions(@PathVariable("status") int status) throws AuthException {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());

//        return success(missionService.getMissions(userInfo.getSn(), status));
        return success(missionService.getMissions(893552080L, status));
    }
}
