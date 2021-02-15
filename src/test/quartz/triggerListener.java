package quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.listeners.TriggerListenerSupport;

import java.util.Calendar;

public class triggerListener extends TriggerListenerSupport {
    @Override
    public String getName() {
        return "不能null";
    }
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        super.triggerFired(trigger, context);
        System.out.println("只要trigger触发就会执行");
    }
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        super.triggerComplete(trigger, context, triggerInstructionCode);
    }
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        Calendar now =  Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        System.out.println("今天是"+day);
        if(day == 6){
            return true;
        }
        return super.vetoJobExecution(trigger, context);
    }
}
