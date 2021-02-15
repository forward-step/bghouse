package quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import quartz.MyJob;

import java.util.Date;

public class JobTest {
    @Test
    public void test1() throws SchedulerException {
        // 配置任务细节
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("unique id", "group")
                // 传递参数给job
                .usingJobData("name","supyp")
                .build();
        // 触发器
        Date date = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);
        Trigger trigger = TriggerBuilder.newTrigger()
                // 1.配置执行时间
                 .startNow() // 立即执行
                .withPriority(5) // 优先级，默认为5
                // 2.传递参数
                .usingJobData("trigger","trigger1")
                // 3.任务周期
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                ).build();
        // 联系起来: 一个jobDetail对应一个trigger
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.getListenerManager().addTriggerListener(new triggerListener());
        scheduler.scheduleJob(jobDetail,trigger);
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
    }
}
