package cn.edu.bit.job_management_backend.constant;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserConstant {
    private String username;
    private String password;
}
