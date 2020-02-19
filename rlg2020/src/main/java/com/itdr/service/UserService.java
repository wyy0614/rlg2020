package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import org.springframework.util.StringUtils;

public interface UserService {
    //用户登陆
    ServerResponse login(String username, String password);
    //用户注册
    ServerResponse<User> register(User u);

    ServerResponse<User> updateInfor(String email, String iphone, String question, String answer,User user);

    ServerResponse<User> check(String str,String type);
}
