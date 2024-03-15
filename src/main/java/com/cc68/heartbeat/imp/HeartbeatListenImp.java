package com.cc68.heartbeat.imp;

import com.cc68.heartbeat.HeartbeatListen;
import com.cc68.manager.ReceiveManager;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.IOException;
@Controller("heartbeatListen")
public class HeartbeatListenImp implements HeartbeatListen {
    /**
     * 服务器对象，可以用于获取一些东西
     * */
    @Autowired
    @Qualifier("server")
    private Server server;
    /**
     * 心跳管理器的接收器
     * */
    @Autowired
    @Qualifier("receiveManagerHeartbeat")
    private ReceiveManager receiveManager;
    /**
     * 用于表示服务器何时停止
     * */
    private boolean flag = true;

    public HeartbeatListenImp(){}

    public HeartbeatListenImp(Server server, ReceiveManager receiveManager) {
        this.server = server;
        this.receiveManager = receiveManager;
    }

    @Override
    public void run() {
        try {
            receiveManager.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String time = MessageUtil.getTime();
        System.out.println(time+"----心跳管理器启动");
        while (flag){
            Message message = receiveManager.listen();
            if (message != null){
                User user = server.getLoginUser().getUser(message.getOriginator());
                if (user!=null){
                    user.refresh();
                }
            }
            try {
                receiveManager.getAccept().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close(){
        flag = false;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ReceiveManager getReceiveManager() {
        return receiveManager;
    }

    public void setReceiveManager(ReceiveManager receiveManager) {
        this.receiveManager = receiveManager;
    }
}
