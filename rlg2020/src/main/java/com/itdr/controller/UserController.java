package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.UserVo;
import com.itdr.service.UserService;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/user")
public class UserController {

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
     * 检查用户名邮箱是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping("checkvalid.do")
    public ServerResponse<User> check(String str,String type){
        return userService.check(str,type);
    }

    /**
     * 获取登陆用户信息
     * @param session
     * @return
     */
    @RequestMapping("getuserinfo.do")
    public ServerResponse<User> getInfor(HttpSession session){
        User user =(User) session.getAttribute("user");
        //判断用户是否登陆
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }
        UserVo userVo = ObjectToVOUtil.userToUserVO(user);
        return ServerResponse.successRS(userVo);
    }


    /**
     * 获取登陆用户的详细信息
     * @param session
     * @return
     */

    @RequestMapping("getinforamtion.do")
    public ServerResponse<User> getAllInfor(HttpSession session){
        //判断用户是否登陆
        User user =(User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }
        return ServerResponse.successRS(user);
    }


    /**
     * 登陆状态更新个人信息
     * @param email
     * @param iphone
     * @param question
     * @param answer
     * @param session
     * @return
     */
    @RequestMapping("updateinformation.do")
    public ServerResponse<User> updateInfor(String email, String iphone, String question, String answer,HttpSession session){
        //判断用户是否登陆
        User user =(User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }

        return userService.updateInfor(email,iphone,question,answer,user);
    }


    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping("logout.do")
    public ServerResponse<User> logout(HttpSession session){
        //判断用户是否登陆
        User user =(User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getDesc());
        }

        session.removeAttribute("user");
        return ServerResponse.successRS(ConstCode.UserEnum.LOGOUT.getDesc());
    }


    /**
     * 忘记密码
     * @param username
     * @return
     */
    @RequestMapping("forget_get_question.do")
    public ServerResponse<User> forgetGetQusetion(String username){
        return userService.forgetGetQusetion(username);
    }


    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping("forget_check_answer.do")
    public ServerResponse<User> forgetCheckAnswer(String username, String question, String answer){
        return userService.forgetCheckAnswer(username,question,answer);
    }


    /**
     * 忘记密码的重设密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping("forget_reset_password.do")
    public ServerResponse<User> forgetResetPassword(String username, String passwordNew, String forgetToken,HttpSession session){
        ServerResponse<User> userServerResponse = userService.forgetResetPassword(username, passwordNew, forgetToken);
        if(userServerResponse.isSuccess()){
            session.removeAttribute("user");
        }
        return userServerResponse;
    }


    @RequestMapping("reset_password.do")
    public ServerResponse<User> resetPassword(String passwordOld, String passwordNew,HttpSession session){
        //判断用户是否登陆
        User user =(User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }

        return userService.resetPassword(user,passwordOld,passwordNew);
    }
}
