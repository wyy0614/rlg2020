package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.UserMapper;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import com.mysql.fabric.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse login(String username, String password) {
        //参数非空判断
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if (StringUtils.isEmpty(password)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                            ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }

        //查询用户
        User u = userMapper.selectByUsernameAndPassword(username, password);
        if (u == null) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.FAIL_LOGIN.getCode(),
                            ConstCode.UserEnum.FAIL_LOGIN.getDesc());
        }
        //返回成功数据
        return ServerResponse.successRS(u);
    }

    /**
     * 用户注册
     *
     * @param u
     * @return
     */
    @Override
    public ServerResponse<User> register(User u) {
        //参数非空判断
        if (StringUtils.isEmpty(u.getUsername())) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }

        if (StringUtils.isEmpty(u.getPassword())) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                            ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }

        if (StringUtils.isEmpty(u.getAnswer())) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_ANSWER.getCode(),
                            ConstCode.UserEnum.EMPTY_ANSWER.getDesc());
        }

        if (StringUtils.isEmpty(u.getQuestion())) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_QUESTION.getCode(),
                            ConstCode.UserEnum.EMPTY_QUESTION.getDesc());
        }

        //查询用户是否存在
        int i = userMapper.selectByUsername(u.getUsername());
        if (i > 0) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EXIST_USER.getCode(),
                            ConstCode.UserEnum.EXIST_USER.getDesc());
        }
        //查找邮箱是否存在

        //注册用户信息
        int insert = userMapper.insert(u);
        if (insert <= 0) {
            return ServerResponse.defeatedRS
                    (ConstCode.DEFAULT_FAIL,
                            ConstCode.UserEnum.FAIL_REGISTER.getDesc());
        }

        return ServerResponse.successRS
                (ConstCode.UserEnum.SUCCESS_USER.getDesc());
    }


    /**
     * 登陆状态更新个人信息
     *
     * @param email
     * @param iphone
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse<User> updateInfor(String email, String iphone, String question, String answer,User user) {

        User u = new User();
        u.setId(user.getId());
        u.setEmail(email);
        u.setIphone(iphone);
        u.setQuestion(question);
        u.setAnswer(answer);
        int i = userMapper.updateByPrimaryKeySelective(u);
        if(i<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc()
            );
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_USERMSG.getDesc()
        );
    }

    /**
     * 检查用户名邮箱是否有效
     * @param str
     * @param type
     * @return
     */

    @Override
    public ServerResponse<User> check(String str,String type) {
        //非空判断
        if (StringUtils.isEmpty(str)) {
            return ServerResponse.defeatedRS
                    (ConstCode.DEFAULT_FAIL,
                            ConstCode.UserEnum.EMPTY_EMAIL.getDesc());
        }
        if (StringUtils.isEmpty(type)) {
            return ServerResponse.defeatedRS
                    (ConstCode.DEFAULT_FAIL,
                            ConstCode.UserEnum.EMPTY_TYPE.getDesc());
        }
        //查找用户名或邮箱是否存在
        int i = userMapper.selectByUsernameOrEmail(str,type);
        if(i > 0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EXIST_EMAILORUSER.getCode(),
                    ConstCode.UserEnum.EXIST_EMAILORUSER.getDesc()
            );
        }
        return ServerResponse.successRS(
             ConstCode.UserEnum.SUCCESS_MSG.getDesc()
        );
    }
}


