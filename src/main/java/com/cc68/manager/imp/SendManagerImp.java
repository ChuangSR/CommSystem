package com.cc68.manager.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.pojo.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 一个发送器
 * */
public class SendManagerImp {
    //此处的socket一般是从ReceiveManager中获取的
    private Socket socket;
    private BufferedWriter writer;
    public SendManagerImp(Socket socket) throws IOException {
        this.socket = socket;
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }
    /**
     * 发送一个数据
     * */
    public void send(Message message) throws IOException {
        String data = JSON.toJSONString(message);
        writer.write(data);
        writer.write("\n");
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
        socket.close();
    }
}
