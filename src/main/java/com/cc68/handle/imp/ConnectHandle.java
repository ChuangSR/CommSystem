package com.cc68.handle.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.handle.Handle;
import com.cc68.manager.ActionManager;
import com.cc68.manager.UserActionManager;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pojo.UserAction;
import com.cc68.pool.ClientThread;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

@Service("connectHandle")
public class ConnectHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Override
    public Message handle(Message message) {
        UserAction action = server.getUserActionManager().get((String) message.getData().get("key"));
        HashMap<String,Object> data = new HashMap<>();
        if (action != null){
            User temp = server.getLoginUser().getUser(message.getOriginator());
            if (temp != null){
                try {
                    server.getLoginUser().deleteUser(temp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            data.put("code","200");
            data.put("message","连接成功！");
            Socket accept = server.getReceiveManager().getAccept();
            ApplicationContext context = server.getContext();
            ClientThread thread = context.getBean("clientThread", ClientThread.class);
            try {
                User user = server.getUser(message.getOriginator());
                thread.setSocket(accept);
                thread.setUser(user);
                server.getPool().add(thread);
                user.setThread(thread);
                thread.init();
                server.getTempUser().delete(user);
                ActionManager actionManager = server.getActionManager();
                actionManager.action("userUpdateBroadcast","connect",user.getAccount());
                server.getLoginUser().addUser(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server.getUserActionManager().delete(action);
        }else {
            data.put("code","500");
            data.put("message","连接失败");
        }
        return MessageUtil.replyMessage(server.getServiceName(),action.getMessage().getReceiver(),"connect",data,true);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ConnectHandle(Server server) {
        this.server = server;
    }

    public ConnectHandle() {
    }
}
