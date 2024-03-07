package com.cc68.manager;

import com.cc68.pojo.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 用于用户的管理，可以是已登录的用户，或者为其他事件出现的用户
 * 这两类用户都会使用这个接口的实现进行存储，但是并不会被存储在同一个实例中
 * 同时，我们要求这个类的实现中的方法需要是线程安全的
 * */
public interface UserManager {
    /**
     * 先容器中添加一个用户
     * */
    void addUser(User user);
    /**
     * 通过账户获取一个用户
     * */
    User getUser(String account);
    /**
     * 获取所有的用户
     * */
    ArrayList<User> getAll();
    /**
     * 删除一个用户
     * */
    void deleteUser(User user) throws IOException;
    /**
     * 获取在线人数
     * */
    int getOnline();
}
