package com.cc68.pool;

import com.cc68.pojo.User;

import java.io.IOException;

public interface ClientThread extends Runnable{
    /**
     * 关闭当前客户端线程
     * */
    void close() throws IOException;
    /**
     * 获取线程的状态
     * */
    boolean getStatus();
    /**
     * 获取一个与其绑定的User对象
     * */
    User getUser();
}
