package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/user")
public class UserContller {

    @Autowired
    UserService userService;


    /**
     * 用户登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse sr = userService.login(username,password);
        //当登陆成功在session中保存数据
        if(sr.isSuccess()) {
            session.setAttribute("user", sr.getData());
        }
        return sr;
    }

    /**
     * 用户注册
     * @param u
     * @return
     */
    @RequestMapping("register.do")
    public ServerResponse<User> register(User u ){
        return userService.register(u);

    }

    /**
     * 检查用户名是否有效
     * @param username
     * @return
     */
    @RequestMapping("checkvalid.do")
    public ServerResponse<User> check(String username){
        return userService.check(username);
    }

    /**
     * 获取登陆用户信息
     * @return
     */
    @RequestMapping("getuserinfo.do")
    public ServerResponse<User> getInfor(String username){
        return userService.getInfor(username);
    }

    /**
     * 获取登陆用户的详细信息
     * @return
     */
    @RequestMapping("getinforamtion.do")
    public ServerResponse<User> getAllInfor(String username){

        return userService.getAllInfor(username);
    }


    /**
     * 登陆状态更新个人信息
     * @param email
     * @param iphone
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping("updateinformation.do")
    public ServerResponse<User> updateInfor(String email, String iphone, String question, String answer,String username){
        return userService.updateInfor(email,iphone,question,answer,username);
    }

    /**
     * 退出登陆
     * @return
     */
    @RequestMapping("logout.do")
    public ServerResponse<User> logout(String username){
        return userService.logout(username);
    }

}
