package sinhan.server1.domain.allowance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.allowance.dto.RegularAllowanceSaveRequestDto;
import sinhan.server1.domain.allowance.service.AllowanceService;
import sinhan.server1.global.utils.ApiUtils;
import static sinhan.server1.global.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("allowances")
public class AllowanceController {

    private final AllowanceService allowanceService;

    //정기 용돈 만들기
    @PostMapping("regular/{fo}")
    public ApiUtils.ApiResult createRegularAllowance(@PathVariable("fo") Integer fo, @Valid @RequestBody RegularAllowanceSaveRequestDto request){
        //token+ fo => childId값 찾아옴
        //token => parentId
        allowanceService.createRegularAllowance(0, 1, request);
        return success(null);
    }

    //정기 용돈 조회하기
    @GetMapping("regular/{fo}")
    public ApiUtils.ApiResult findRegularAllowance(){
       return success(null);
    }

    //정기 용돈 변경하기

    //정기 용돈 삭제하기

    //용돈 조르기 수락,거절 => 수락시의 송금까지 완료

    //용돈 조르기 내역 조회
}
