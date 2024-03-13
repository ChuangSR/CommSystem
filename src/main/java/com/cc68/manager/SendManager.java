package com.cc68.manager;

import com.cc68.pojo.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * 这个对象用于向User对象发送消息，被集成在了User中
 * */
public interface SendManager {
    void init(Socket socket) throws IOException;
    /**
     * 发送消息
     * */
    void send(Message message) throws IOException;
    /**
     * 关闭
     * */
    void close() throws IOException;
}
