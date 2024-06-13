package sinhan.server1.global.config.quartz.allowance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.allowance.dto.RegularAllowanceSaveRequestDto;
import sinhan.server1.domain.allowance.entity.Allowance;

import static org.quartz.JobKey.jobKey;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllowanceScheduleService {
    private final Scheduler scheduler;
    private final AllowanceTriggerService allowanceTriggerService;
    private final AllowanceJobDetailService allowanceJobDetailService;

    public void startSchedule(Allowance allowance, RegularAllowanceSaveRequestDto request){
        applySchedule(allowance, request);
    }

    private void applySchedule(Allowance allowance, RegularAllowanceSaveRequestDto request){
        String parentUniqueId = String.valueOf(allowance.getParents().getUniqueId());
        String childUniqueId = String.valueOf(allowance.getChild().getUniqueId());
        JobKey jobkey = jobKey(
                parentUniqueId+childUniqueId,
                "allowanceTransactions"
                );

        //JobDetail
        JobDetail allowanceJobDetail = allowanceJobDetailService.build(jobkey, allowance, request);
        Trigger allowanceTrigger = allowanceTriggerService.build(jobkey, allowance.getCreateDate());

    }
}
