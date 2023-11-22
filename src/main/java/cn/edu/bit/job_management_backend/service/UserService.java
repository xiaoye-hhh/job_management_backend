package cn.edu.bit.job_management_backend.service;

import cn.edu.bit.job_management_backend.constant.UserConstant;
import cn.edu.bit.job_management_backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserConstant userInfo;

    public boolean login(User user){
        return userInfo.getUsername().equals(user.getUsername()) &&
               userInfo.getPassword().equals(user.getPassword());
    }

}
