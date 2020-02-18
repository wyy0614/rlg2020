package com.itdr.mapper;

import com.itdr.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsernameAndPassword(String username, String password);

    int selectByUsername(String username);

    User selectBystate(String username);

    User selectAllBystate(String username);

    int updateInfor(String username,String email, String iphone, String question, String answer);

    int updateState(String username);

}