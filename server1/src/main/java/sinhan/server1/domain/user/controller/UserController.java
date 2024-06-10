package sinhan.server1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.auth.entity.FamilyInfo;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static sinhan.server1.global.utils.ApiUtils.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ApiUtils.ApiResult<User> getUser(@PathVariable("id") int id) throws AuthException {
        Map<String, Object> userInfo = jwtService.getUserInfo();
        if ((int) userInfo.get("userId") != id) {
            List<FamilyInfo> familyInfo = (List<FamilyInfo>) userInfo.get("familyInfo");

            Set<Integer> familyIds = new java.util.HashSet<>();
            for (FamilyInfo info : familyInfo) {
                familyIds.add(info.getId());
            }

            if (!familyIds.contains(id)) {
                throw new AuthException("UNAUTHORIZED");
            }
        }

        User user = userService.getUser(id);
        return success(user);
    }

}
