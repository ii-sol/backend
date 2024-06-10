package sinhan.server1.domain.user.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.JwtService;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static sinhan.server1.global.utils.ApiUtils.error;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @GetMapping("/{id}")
    public ApiUtils.ApiResult<String> getUser(@PathVariable("id") int id) throws AuthException {
//        try {
//            Map<String, Object> userInfo = jwtService.getUserInfo();
//            if ((int) userInfo.get("userId") != id) {
//                List<Map<Integer, String>> familyInfo = (List<Map<Integer, String>>) userInfo.get("familyInfo");
//
//                Set familyIds;
//                for (Map<Integer, String> info : familyInfo) {
//                    familyIds.add(info.k)
//                }
//            }
//
//        } catch (AuthException e) {
//            throw new AuthException("");
//        }

        return error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

}
