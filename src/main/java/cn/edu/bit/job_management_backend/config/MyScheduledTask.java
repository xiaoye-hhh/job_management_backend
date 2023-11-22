package cn.edu.bit.job_management_backend.config;

import cn.edu.bit.job_management_backend.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduledTask {

    @Autowired
    private JobService jobService;

    // 每隔5秒执行一次
    @Scheduled(fixedRate = 5000)
    public void task1() {
        jobService.tryRunNewJob();
    }
}
