package sinhan.server1.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.user.dto.FamilyFindOneResponse;
import sinhan.server1.domain.user.dto.FamilySaveRequest;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserUpdateRequest;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;
import sinhan.server1.global.utils.exception.AuthException;
import sinhan.server1.global.utils.exception.SqlException;

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

    @PostMapping("")
    public ApiUtils.ApiResult connectFamily(@RequestBody FamilySaveRequest familySaveRequest) throws SqlException {
        //        Map<String, Object> userInfo = jwtService.getUserInfo();
        //        if ((int) userInfo.get("userId") == id)
        // int role = userInfo.get("role")

        int id = 2; // jwt 연결하기전까지 임시 데이터 사용
        int role = 1; // jwt 연결하기전까지 임시 데이터 사용
        familySaveRequest.setId(id);
        familySaveRequest.setRole(role);
        try {
            FamilyFindOneResponse family = userService.connectFamily(familySaveRequest);
            if (family != null) {
                return success("가족 관계가 생성되었습니다.");
            } else {
                return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (SqlException e) {
            return error("이미 존재하는 가족 관계입니다.", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{fo}")
    public ApiUtils.ApiResult disconnectFamily(@PathVariable int fo) {
        //        Map<String, Object> userInfo = jwtService.getUserInfo();

        int id = 1; // jwt 연결하기전까지 임시 데이터 사용
        int familyId = 3; // jwt 연결하기전까지 임시 데이터 사용
        int role = 1; // jwt 연결하기전까지 임시 데이터 사용

        if (role == 1) {
            userService.disconnectFamily(id, familyId);
        } else {
            userService.disconnectFamily(familyId, id);
        }


        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
