package sinhan.server1.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.dto.AccountTransmitOneRequest;
import sinhan.server1.domain.account.dto.AccountTransmitOneResponse;
import sinhan.server1.domain.account.service.AccountService;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.global.utils.ApiUtils;

import java.util.List;

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
    @PostMapping("transmit")
    public ApiUtils.ApiResult transmitMoney(TempUser tempUser, @RequestBody AccountTransmitOneRequest transmitRequest){
        AccountTransmitOneResponse response = accountService.transmitMoney(transmitRequest);
        return success(response);
    }

    //계좌 내역 보기 => response 형태 이게 맞나 모르겠
    @GetMapping("history")
    public ApiUtils.ApiResult findAccountHistory(TempUser tempUser, @RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer status){
        List<AccountHistoryFindAllResponse> response = accountService.findAccountHistory(tempUser.getId(), year, month, status);
        return success(response);
    }


}
