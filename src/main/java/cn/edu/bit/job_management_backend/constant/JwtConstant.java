package cn.edu.bit.job_management_backend.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConstant {
    private String secretKey; // jwt密钥
    private Integer ttlMillis; // jwt过期时间(毫秒)
    private String tokenName; // header中的字段名
}
