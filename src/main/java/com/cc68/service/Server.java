package com.cc68.service;

import com.cc68.manager.*;
import com.cc68.mapper.MessageMapper;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.User;
import com.cc68.pool.ClientThreadPool;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;

public interface Server extends Runnable{
    /**
     * 初始化
     * */
    void init() throws IOException;
    /**
     * 服务器关闭
     * */
    void close() throws IOException;
    /**
     * 获取服务器的名称
     * */
    String getServiceName();
    /**
     * 获取服务器已经登录的用户
     * */
    UserManager getLoginUser();
    /**
     * 获取服务器的临时用户
     * */
    UserManager getTempUser();
    /**
     * 获取消息构造器
     * */
    MessageHandleManager getMessageHandleManager();
    /**
     * 获取线程池
     * */
    ClientThreadPool getPool();
    /**
     * 获取dao
     * */
    UserMapper getUserMapper();

    ApplicationContext getContext();
    void setContext(ApplicationContext context);
    ReceiveManager getReceiveManager();
    UserActionManager getUserActionManager();

    User getUser(String account);

    MessageMapper getMessageMapper();

    ActionManager getActionManager();
    HashMap<String, String> getRegisterUsers();
}
