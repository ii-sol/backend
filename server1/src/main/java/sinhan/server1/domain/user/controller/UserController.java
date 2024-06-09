package sinhan.server1.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.user.dto.UserFindOneResponse;
import sinhan.server1.domain.user.dto.UserFindRequest;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;

import static sinhan.server1.global.utils.ApiUtils.success;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @PostMapping("/login")
    public ApiUtils.ApiResult<UserFindOneResponse> login(@Valid @RequestBody UserFindRequest userFindRequest){
        return success(userService.login(userFindRequest));
    }
}
