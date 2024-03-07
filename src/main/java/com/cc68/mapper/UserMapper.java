package com.cc68.mapper;

import com.cc68.pojo.User;

import java.util.ArrayList;

public interface UserMapper {
    //根据账号查询账户
    User select(User user);
    //查询所有账户
    ArrayList<User> selectAll();
    //删除账户
    int delete(User user);
    //更新账户
    int update(User user);
    //添加账户
    int insert(User user);
}
