package com.itdr.utils;

import com.itdr.pojo.User;
import com.itdr.pojo.bo.UserVo;

public class ObjectToVOUtil {


    public static UserVo userToUserVO(User u){
        UserVo uv =new UserVo();
        uv.setId(u.getId());
        uv.setUsername(u.getUsername());
        uv.setEmail(u.getEmail());
        uv.setIphone(u.getIphone());
        uv.setCreateTime(u.getCreateTime());
        uv.setUpdateTime(u.getUpdateTime());

        return uv;
    }
}
