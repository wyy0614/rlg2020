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
     * 查看登陆用户信息
     *
     * @return
     */
    @Override
    public ServerResponse<User> getInfor(String username) {
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        User u = userMapper.selectBystate(username);
        if (u == null) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.FORCE_EXIT.getCode(),
                            ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }
        return ServerResponse.successRS(u);
    }

    /**
     * 查看登陆用户详细信息
     *
     * @return
     */
    @Override
    public ServerResponse<User> getAllInfor(String username) {
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        User u = userMapper.selectAllBystate(username);
        if (u == null) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.FORCE_EXIT.getCode(),
                            ConstCode.UserEnum.FORCE_EXIT.getDesc());
        }
        return ServerResponse.successRS(u);
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
    public ServerResponse<User> updateInfor(String email, String iphone, String question, String answer,String username) {
        //非空判断
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if (StringUtils.isEmpty(email)) {
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_EMAIL.getCode(),
                    ConstCode.UserEnum.EMPTY_EMAIL.getDesc()
            );
        }
        if (StringUtils.isEmpty(iphone)) {
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_IPHONE.getCode(),
                    ConstCode.UserEnum.EMPTY_IPHONE.getDesc()
            );
        }
        if (StringUtils.isEmpty(answer)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_ANSWER.getCode(),
                            ConstCode.UserEnum.EMPTY_ANSWER.getDesc());
        }
        if (StringUtils.isEmpty(question)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_QUESTION.getCode(),
                            ConstCode.UserEnum.EMPTY_QUESTION.getDesc());
        }

        int update = userMapper.updateInfor(username,email,iphone,question,answer);
        if(update <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc()
            );
        }
        return ServerResponse.successRS(
                ConstCode.DEFAULT_SUCCESS,
                ConstCode.UserEnum.SUCCESS_USERMSG.getDesc()
        );
    }

    /**
     * 退出登陆
     * @return
     */
    @Override
    public ServerResponse<User> logout(String username) {
        //非空判断
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        int update = userMapper.updateState(username);
        if(update <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.UNLAWFULNESS_TOKEN.getDesc()
            );
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.LOGOUT.getDesc()
        );
    }

    @Override
    public ServerResponse<User> check(String username) {
        //非空判断
        if (StringUtils.isEmpty(username)) {
            return ServerResponse.defeatedRS
                    (ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                            ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        int i = userMapper.selectByUsername(username);
        if(i > 0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EXIST_USER.getCode(),
                    ConstCode.UserEnum.EXIST_USER.getDesc()
            );
        }
        return ServerResponse.successRS(
             ConstCode.UserEnum.SUCCESS_MSG.getDesc(),
             ConstCode.UserEnum.SUCCESS_MSG.getDesc()
        );
    }
}


