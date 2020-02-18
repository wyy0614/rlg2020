package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import org.springframework.util.StringUtils;

public interface UserService {
    //用户登陆
    ServerResponse login(String username, String password);
    //用户注册
    ServerResponse<User> register(User u);
    //查看登陆用户信息
    ServerResponse<User> getInfor(String username);

    ServerResponse<User> getAllInfor(String username);

    ServerResponse<User> updateInfor(String email, String iphone, String question, String answer, String username);

    ServerResponse<User> logout(String username);

    ServerResponse<User> check(String username);
}
