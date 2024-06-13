package sinhan.server1.global.config.quartz.allowance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.allowance.dto.RegularAllowanceSaveRequestDto;
import sinhan.server1.domain.allowance.entity.Allowance;

import static org.quartz.JobBuilder.newJob;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllowanceJobDetailService {
    public JobDetail build(JobKey jobKey, Allowance allowance, RegularAllowanceSaveRequestDto request){

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("senderUniqueId", allowance.getParents().getUniqueId());
        jobDataMap.put("recieverUniqueId", allowance.getChild().getUniqueId());
        jobDataMap.put("amount", request.getAmount()); //request에서 빼올까 아님 allowance에서 빼올까
        jobDataMap.put("type", allowance.getType());

        return newJob(RegularAllowanceJob.class)
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .storeDurably(true)
                .usingJobData(jobDataMap)
                .build();
    }
}
