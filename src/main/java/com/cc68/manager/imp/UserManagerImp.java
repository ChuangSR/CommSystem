package com.cc68.manager.imp;

import com.cc68.manager.UserManager;
import com.cc68.pojo.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 用于统一管理登录的用户
 */
@Controller("userManager")
@Scope("prototype")
public class UserManagerImp implements UserManager {
    private final ArrayList<User> users = new ArrayList<>(100);
    /**
     * 将用户添加到管理器中
     * */
    public synchronized void addUser(User user){
        User temp = getUser(user.getAccount());
        if (temp != null){
            try {
                deleteUser(temp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        deleteUser(user.getAccount());
    }

    @Override
    public synchronized void deleteUser(String account) throws IOException {
        User user = getUser(account);
        if (user != null){
            user.close();
            users.remove(user);
        }
    }

    @Override
    public synchronized void deleteUser(ArrayList<User> temp) throws IOException {
        for (User user:temp){
            deleteUser(user);
        }
    }

    @Override
    public synchronized void delete(User user) {
        users.remove(user);
    }

    public synchronized int getOnline(){
        return users.size();
    }

    @Override
    public void close() {
        for (User user:users){
            try {
                user.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
