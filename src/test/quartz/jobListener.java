package quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

public class jobListener extends JobListenerSupport {
    @Override
    public String getName() {
        return "不能为null";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        super.jobWasExecuted(context, jobException);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
    }
}
