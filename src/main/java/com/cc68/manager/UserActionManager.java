package com.cc68.manager;

import com.cc68.pojo.UserAction;

/**
 * 用于管理用户的部分行为
 * */
public interface UserActionManager extends Runnable{
    void init();
    UserAction get(String key);
    void add(UserAction action);
    void delete(UserAction action);
    void close();
}
