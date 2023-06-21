package com.stucpt.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: sky
 * @Date: 2023/03/16/11:54
 * @Description:
 */
@Component
public class TestJob {

    @Scheduled(cron = "0/5 * * * * ?")
    public  void testJob(){
        System.out.println("定时任务执行了");
    }
}
