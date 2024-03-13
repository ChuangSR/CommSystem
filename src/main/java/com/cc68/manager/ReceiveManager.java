package com.cc68.manager;

import com.cc68.pojo.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 用于接收数据的一个类
 * */
public interface ReceiveManager {
    /**
     * 获取一个ServerSocket对象
     * */
    ServerSocket getServerSocket();
    /**
     * 获取一个消息发送者的socket
     * */
    Socket getAccept();
    /**
     * 监听，获取被发送过来的数据
     * */
    Message listen();
    /**
     * 关闭程序
     * */
    void close() throws IOException;

    void init() throws IOException;
}
