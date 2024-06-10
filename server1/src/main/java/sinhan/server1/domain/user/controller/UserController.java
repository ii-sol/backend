package sinhan.server1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;
import sinhan.server1.global.utils.exception.AuthException;

import static sinhan.server1.global.utils.ApiUtils.error;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @GetMapping("")
    public ApiUtils.ApiResult getUser(){
//        try {
//            int userId = jwtService.getUserInfo().get("userId");
//        } catch (AuthException e) {
//            throw new AuthException("INVALID_JWT");
//        }

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

}
