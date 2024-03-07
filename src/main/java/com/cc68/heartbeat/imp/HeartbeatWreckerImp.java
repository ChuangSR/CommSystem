package com.cc68.heartbeat.imp;

import com.cc68.heartbeat.HeartbeatWrecker;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
@Controller("heartbeatWrecker")
public class HeartbeatWreckerImp implements HeartbeatWrecker {
    private Server server;
    private int heartbeatTime;

    private boolean flag = true;

    public HeartbeatWreckerImp(){}

    public HeartbeatWreckerImp(Server server, int heartbeatTime) {
        this.server = server;
        this.heartbeatTime = heartbeatTime;
    }

    @Override
    public void run() {
        String time = server.getMessageHandleManager().getBuilder().getTime();
        System.out.println(time+"----摧毁程序启动");
        while (flag){
            try {
                Thread.sleep(1000);
                ArrayList<User> users = server.getLoginUser().getAll();
                for (User user:users){
                    long heartbeat = user.getLinkTime();
                    if (heartbeat - System.currentTimeMillis()/1000 > heartbeatTime){
                        server.getLoginUser().deleteUser(user);
                    }
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() {
        flag = false;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public int getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(int heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

}
