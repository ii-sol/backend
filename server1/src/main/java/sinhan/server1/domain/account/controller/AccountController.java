package sinhan.server1.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.service.AccountService;
import sinhan.server1.domain.tempuser.TempUser;
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
    public ApiUtils.ApiResult findAccount(TempUser tempUser, @PathVariable("ao") Integer ao){
        AccountFindOneResponse response = accountService.findAccount(tempUser.getId(), ao);
        return success(response);
    }

    //이체하기

    //계좌 내역 보기
    @GetMapping("history")
    public ApiUtils.ApiResult findAccountHistory(TempUser tempUser, @RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer status){
        AccountHistoryFindAllResponse response = accountService.findAccountHistory(tempUser.getId(), year, month, status);
        return success(response);
    }


}
