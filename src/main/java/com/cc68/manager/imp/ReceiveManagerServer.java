package com.cc68.manager.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.manager.ReceiveManager;
import com.cc68.pojo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * 一个接收器
 * */
@Controller("receiveManagerServer")
public class ReceiveManagerServer implements ReceiveManager {
    @Value("${serverPort}")
    private int port;

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

    public ReceiveManagerServer() {
    }

    public ReceiveManagerServer(int port) {
        this.port = port;
    }

    public void init() throws IOException {
        serverSocket = new ServerSocket(port);
    }
    /**
     * 监听端口
     * */
    public Message listen(){
        Message message = null;
        try {
            accept = serverSocket.accept();
            System.out.println(Arrays.toString(accept.getInetAddress().getAddress()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            message = JSON.parseObject(reader.readLine(), Message.class);
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return message;
    }

    public void close() throws IOException {
        accept.close();
        serverSocket.close();
    }
}
