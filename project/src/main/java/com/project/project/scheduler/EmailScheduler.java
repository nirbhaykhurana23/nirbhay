package com.project.project.scheduler;

import com.project.project.services.DailyOrderStatusEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync(proxyTargetClass = true)
@Component
public class EmailScheduler {

    @Autowired
    private DailyOrderStatusEmailService dailyOrderStatusEmailService;

    @Scheduled(cron = "0 58 3 * * ?")

    public void run(){
        dailyOrderStatusEmailService.saveEmailReport();
    }
}
