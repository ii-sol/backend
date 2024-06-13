package sinhan.server1.global.config.quartz.allowance;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.allowance.dto.RegularAllowanceSaveRequestDto;
import sinhan.server1.global.utils.UtilMethod;

@Slf4j
@Component
@DisallowConcurrentExecution // 스케줄러 중복 실행 방지
@PersistJobDataAfterExecution // jobDataMap에 영속성 부여 정보 변경 가능하게 한다.
public class RegularAllowanceJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException{
       JobDataMap mergedJobDataMap = context.getMergedJobDataMap();

    }

    private void transmitMoneyByUserId(RegularAllowanceSaveRequestDto request, int recieverUniqueId, int senderUniqueId,int amount, int status){
        return;
    }

}
