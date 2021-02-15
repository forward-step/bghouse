package com.supyp.bghouse.job;

import java.time.LocalTime;

public class MyJob2 {
    public void execute(){
        LocalTime now = LocalTime.now();
        System.out.println(now.toString()+"myjob2正在执行");
    }
}
