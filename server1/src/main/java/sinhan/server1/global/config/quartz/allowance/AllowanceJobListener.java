package sinhan.server1.global.config.quartz.allowance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;

@Slf4j
@RequiredArgsConstructor
public class AllowanceJobListener implements JobListener {
    @Override
    public String getName() {
        return null;
    }

    //Job 수행전
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        // 여기서 이체?
    }

    //Job 중단시
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobKey key = context.getJobDetail().getKey();
        log.info("중단된 job의 jobkey = {}", key);
    }

    //Job실행 후 예외 발생
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

    }
}
