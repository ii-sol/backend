package sinhan.server1.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.service.AccountService;
import sinhan.server1.domain.user.User;
import sinhan.server1.global.utils.ApiUtils;

import static sinhan.server1.global.utils.ApiUtils.success;

@RestController
@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService; // 생성자 주입

    //계좌 개별 조회하기
    @GetMapping("{ao}")
    public ApiUtils.ApiResult findAccount(User user, @PathVariable("ao") Integer ao){
        AccountFindOneResponse response = accountService.findAccount(user.getId(), ao);
        return success(response);
    }

    //이체하기



}
