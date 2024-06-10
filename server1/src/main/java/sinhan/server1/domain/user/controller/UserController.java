package sinhan.server1.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.auth.entity.FamilyInfo;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserUpdateRequest;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static sinhan.server1.global.utils.ApiUtils.error;
import static sinhan.server1.global.utils.ApiUtils.success;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ApiUtils.ApiResult getUser(@PathVariable("id") int id) throws AuthException {
//        Map<String, Object> userInfo = jwtService.getUserInfo();
//        if ((int) userInfo.get("userId") != id) {
//            List<FamilyInfo> familyInfo = (List<FamilyInfo>) userInfo.get("familyInfo");
//
//            Set<Integer> familyIds = new java.util.HashSet<>();
//            for (FamilyInfo info : familyInfo) {
//                familyIds.add(info.getId());
//            }
//
//            if (!familyIds.contains(id)) {
//                throw new AuthException("UNAUTHORIZED");
//            }
//        }

        UserFindOneResponse user = userService.getUser(id);
        return user.getId() == id ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ApiUtils.ApiResult updateUser(@RequestBody UserUpdateRequest userUpdateRequest) throws AuthException {
//        Map<String, Object> userInfo = jwtService.getUserInfo();
//        if ((int) userInfo.get("userId") == id)

        int id = 1; // jwt 연결하기전까지 임시 데이터 사용
        userUpdateRequest.setId(id);
        UserFindOneResponse user = userService.updateUser(userUpdateRequest);
        return user != null ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
