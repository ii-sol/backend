package sinhan.server1.global.config.quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("allowances")
public class QuartzController {

    @Autowired
    private CustomQuartzScheduler customQuartzScheduler;

    @PostMapping("/scheduleJob")
    public String scheduleJob(@RequestBody String cronExpression) {
        customQuartzScheduler.scheduleJobWithCron(cronExpression);
        return "Quartz job scheduled successfully with cron expression: " + cronExpression;
    }
}
