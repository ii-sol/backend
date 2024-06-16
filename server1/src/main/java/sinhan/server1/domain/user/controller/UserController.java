package sinhan.server1.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.auth.dto.FamilyInfoResponse;
import sinhan.server1.domain.auth.dto.UserInfoResponse;
import sinhan.server1.domain.user.dto.*;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.security.JwtService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
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

    @GetMapping("/{sn}")
    public ApiUtils.ApiResult getUser(@PathVariable("sn") long sn) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());
        if (userInfo.getSn() != sn) {
            List<FamilyInfoResponse> familyInfo = userInfo.getFamilyInfo();

            Set<Long> familyIds = new java.util.HashSet<>();
            for (FamilyInfoResponse info : familyInfo) {
                familyIds.add(info.getSn());
            }

            if (!familyIds.contains(sn)) {
                throw new AuthException("UNAUTHORIZED");
            }
        }

        UserFindOneResponse user = userService.getUser(sn);
        return user.getSerialNumber() == sn ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ApiUtils.ApiResult updateUser(@RequestBody UserUpdateRequest userUpdateRequest) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());
        userUpdateRequest.setSerialNum(userInfo.getSn());

        UserFindOneResponse user = userService.updateUser(userUpdateRequest);
        return user != null ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{family-sn}")
    public ApiUtils.ApiResult disconnectFamily(@PathVariable int familySn) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());

        if (!isFamilyUser(familySn)) {
            throw new NoSuchElementException("부모 사용자가 존재하지 않습니다.");
        }

        if (isConnected(familySn)) {
            boolean isDisconnected = userService.disconnectFamily(userInfo.getSn(), familySn);

            if (isDisconnected) {
                return success("가족 관계가 삭제되었습니다.");
            }
        }

        return error("가족 관계가 삭제되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isFamilyUser(long familySn) {
        // TODO: 부모 사용자 확인 이벤트 등록 - 콜백
        return true;
    }

    private boolean isConnected(long familySn) {
        // TODO: 부모 서버 가족 관계 생성 이벤트 등록 - 콜백
        return true;
    }

    @GetMapping("/phones")
    public ApiUtils.ApiResult getPhones() {
        List<String> phones = userService.getPhones();

        return phones.isEmpty() ? error("전화번호부를 가져오지 못했습니다.", HttpStatus.NOT_FOUND) : success(phones);
    }
}
