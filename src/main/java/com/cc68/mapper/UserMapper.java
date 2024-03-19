package com.cc68.mapper;

import com.cc68.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

//@Transactional
public interface UserMapper {
    //根据账号查询账户
    User select(User user);
    //查询所有账户
    ArrayList<User> selectAll();
    //删除账户
    int delete(String account);
    //更新账户
    int update(@Param("account")String account,@Param("password")String password);
    //添加账户
    int insert(User user);
}
