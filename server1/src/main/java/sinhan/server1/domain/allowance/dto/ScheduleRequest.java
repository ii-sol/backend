package sinhan.server1.domain.allowance.dto;

public class ScheduleRequest {
    private String cronExpression;

    // Getter and Setter
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
