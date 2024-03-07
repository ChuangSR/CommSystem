package com.cc68.pool.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pool.ClientThread;
import com.cc68.service.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThreadImp implements ClientThread {
    private Server server;
    private User user;
    private Socket socket;
    private BufferedReader reader;
    /**
     * 线程的状态
     * */
    private boolean flag = true;


    public ClientThreadImp(){}

    public ClientThreadImp(Server server, User user, Socket socket) throws IOException {
        this.server = server;
        this.user = user;
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (flag){
            user.refresh();
            try {
                String data = reader.readLine();
                if (data == null){
                    continue;
                }
                Message message = JSON.parseObject(data, Message.class);
//                MessageBean replyBean = HandleMessage.handle(bean, userBean,server);
//                boolean flag = Boolean.parseBoolean(replyBean.getData().get("flag"));
//                if (flag){
//                    userBean.getSendManager().send(replyBean);
//                }
//                ConsoleMessageManger.send(HandleMessage.getLog());
            } catch (IOException e) {
                return;
            }
        }
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean getStatus() {
        return flag;
    }

    public void close() throws IOException {
        flag =false;
        reader.close();
        socket.close();
    }
}
