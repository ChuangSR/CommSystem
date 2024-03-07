package com.cc68.manager.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.manager.ReceiveManager;
import com.cc68.pojo.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * 一个接收器
 * */
public class ReceiveManagerImp implements ReceiveManager {

    private ServerSocket serverSocket;

    private Socket accept = null;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    /**
     * 用于获取发送者
     * */
    public Socket getAccept() {
        return accept;
    }

    public ReceiveManagerImp() {
    }

    public ReceiveManagerImp(Integer port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * 监听端口
     * */
    public Message listen(){
        Message message = null;
        try {
            accept = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            message = JSON.parseObject(reader.readLine(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void close() throws IOException {
        accept.close();
        serverSocket.close();
    }
}
