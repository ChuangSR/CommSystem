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
@Controller("receiveManagerHeartbeat")
public class ReceiveManagerHeartbeat implements ReceiveManager {
    @Value("${heartbeatReceiveManagerPort}")
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

    public ReceiveManagerHeartbeat() {
    }

    public ReceiveManagerHeartbeat(int port) {
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
