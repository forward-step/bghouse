import com.supyp.bghouse.utils.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    @Test
    public void now(){
        // 2020-11-11 10:41:00
        System.out.println(DateUtil.LocalDateAndTime());
    }
    @Test
    public void string2date() throws ParseException {
        System.out.println(DateUtil.string2Date("2020-11-11"));
    }
    @Test
    public void passtime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 =  formatter.parse("2020-07-01");
        Date date2 =  formatter.parse("2020-06-30");
        Date date3 =  formatter.parse("2021-07-01");
        int a = DateUtil.getMonthDiff(date1,date2);
        int b = DateUtil.getMonthDiff(date2,date1);
        int c = DateUtil.getMonthDiff(date3,date1);
        int d = DateUtil.getMonthDiff(date1,date3);
        System.out.println("相差月份："+a); // 0
        System.out.println("相差月份："+c); // 12
    }
    // 获取当天的日期和前一天的日期
    @Test
    public void asd(){
        System.out.println(DateUtil.getPassDate(1));
        System.out.println(DateUtil.getPassDate(2));
        System.out.println(DateUtil.getPassDate(3));
        System.out.println(DateUtil.getPassDate(4));
    }
    // 获取七天前的日期
    @Test
    public void qwe(){
        System.out.println(DateUtil.getPassDate(Integer.valueOf("0")));
    }

    @Test
    public void qwea(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getYear() + "-" + now.getDayOfMonth() + "-01");
        System.out.println(now.getYear() + "-" + now.getDayOfMonth() + "-31");
        System.out.println(DateUtil.date2String(new Date()));
    }
}
