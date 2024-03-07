package com.cc68.manager.imp;

import com.cc68.manager.UserManager;
import com.cc68.pojo.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 用于统一管理登录的用户
 */
public class UserManagerImp implements UserManager {
    private final ArrayList<User> users = new ArrayList<>(100);
    /**
     * 将用户添加到管理器中
     * */
    public synchronized void addUser(User user){
        users.add(user);
    }
    /**
     * 获取一个在线用户，如果该用户不在线，那么返回null
     * */
    public synchronized User getUser(String account){
        User reply = null;
        for (User user:users){
            if (user.getAccount().equals(account)){
                reply = user;
                break;
            }
        }
        return reply;
    }

    public synchronized ArrayList<User> getAll(){
        return users;
    }

    public synchronized void deleteUser(User user) throws IOException {
        user.close();
        users.remove(user);
    }
    public synchronized int getOnline(){
        return users.size();
    }

}
