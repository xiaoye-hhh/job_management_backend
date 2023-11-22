package cn.edu.bit.job_management_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobManagementBackendApplication.class, args);
    }

}
