package cn.edu.bit.job_management_backend.controller;

import cn.edu.bit.job_management_backend.constant.JwtConstant;
import cn.edu.bit.job_management_backend.constant.ResultConstant;
import cn.edu.bit.job_management_backend.constant.UserConstant;
import cn.edu.bit.job_management_backend.pojo.User;
import cn.edu.bit.job_management_backend.service.UserService;
import cn.edu.bit.job_management_backend.util.JwtUtil;
import cn.edu.bit.job_management_backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConstant jwtConstant;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (userService.login(user)) {
            Map<String, Object> claims = new HashMap<>();
            String jwt = JwtUtil.createJWT(jwtConstant.getSecretKey(), jwtConstant.getTtlMillis(), claims);
            return Result.success(jwt);
        }else{
            return Result.error(ResultConstant.LOGIN_ERROR);
        }
    }
}
