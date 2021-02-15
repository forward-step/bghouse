package quartz;

import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.Random;

// 防止定时任务的并发
//@DisallowConcurrentExecution
// 表示JobDataMap可以被更新
@PersistJobDataAfterExecution
public class MyJob implements Job {
    @Setter@Getter
    private String name;
    @Setter@Getter
    private String trigger;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalTime now = LocalTime.now();
        System.out.println(now.toString()+", msg=我是"+trigger+"触发的");
    }
}
