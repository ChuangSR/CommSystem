package com.cc68.pool.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pool.ClientThread;
import com.cc68.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
@Controller("clientThread")
@Scope("prototype")
public class ClientThreadImp implements ClientThread {
    @Autowired
    @Qualifier("server")
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

    public void init(){
        Thread thread = new Thread(this);
        thread.start();
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
                Message handle = server.getMessageHandleManager().handle(message);
                if (handle!=null&&handle.isReply()){
                    user.getSendManager().send(handle);
                }
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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void close() throws IOException {
        flag =false;
        reader.close();
        socket.close();
    }
}
