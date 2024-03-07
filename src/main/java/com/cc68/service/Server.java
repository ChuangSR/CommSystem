package com.cc68.service;

import com.cc68.builder.MessageBuilder;
import com.cc68.manager.MessageHandleManager;
import com.cc68.manager.UserManager;
import com.cc68.mapper.UserMapper;
import com.cc68.pool.ClientThreadPool;

public interface Server{
    /**
     * 初始化
     * */
    void init();
    /**
     * 服务器启动
     * */
    void start();
    /**
     * 服务器关闭
     * */
    void close();
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
}
