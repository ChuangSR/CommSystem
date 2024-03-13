package com.cc68.heartbeat.imp;

import com.cc68.heartbeat.HeartbeatWrecker;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
@Controller("heartbeatWrecker")
public class HeartbeatWreckerImp implements HeartbeatWrecker {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Value("${HeartbeatTime}")
    private int heartbeatTime;

    private boolean flag = true;

    public HeartbeatWreckerImp(){}

    public HeartbeatWreckerImp(Server server, int heartbeatTime) {
        this.server = server;
        this.heartbeatTime = heartbeatTime;
    }

    @Override
    public void run() {
        String time = MessageUtil.getTime();
        System.out.println(time+"----摧毁程序启动");
        while (flag){
            try {
                Thread.sleep(1000);
                ArrayList<User> users = server.getLoginUser().getAll();
                ArrayList<User> temp = new ArrayList<>();
                for (User user:users){
                    long heartbeat = user.getLinkTime();
                    if (System.currentTimeMillis()/1000 - heartbeat > heartbeatTime){
                        System.out.println(user.getAccount()+"下线！");
                        temp.add(user);
                    }
                }
                server.getLoginUser().deleteUser(temp);
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
