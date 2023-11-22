package cn.edu.bit.job_management_backend.config;

import cn.edu.bit.job_management_backend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    /**
     * 注册自定义拦截器
     * @param registry
     */
    @Autowired
    private JwtInterceptor jwtInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");
    }
}
