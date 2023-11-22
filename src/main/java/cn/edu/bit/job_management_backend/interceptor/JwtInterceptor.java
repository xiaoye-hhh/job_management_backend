package cn.edu.bit.job_management_backend.interceptor;

import cn.edu.bit.job_management_backend.constant.JwtConstant;
import cn.edu.bit.job_management_backend.constant.UserConstant;
import cn.edu.bit.job_management_backend.util.JwtUtil;
import com.sun.prism.impl.BaseContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtConstant jwtConstant;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        String token = request.getHeader(jwtConstant.getTokenName());

        try {
            Claims claims = JwtUtil.parseJWT(jwtConstant.getSecretKey(), token);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
