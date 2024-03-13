package com.cc68.handle.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.handle.Handle;
import com.cc68.manager.MessageHandleManager;
import com.cc68.manager.SendManager;
import com.cc68.manager.UserManager;
import com.cc68.manager.imp.SendManagerImp;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pojo.UserAction;
import com.cc68.pool.ClientThread;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service("loginHandle")
public class LoginHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Value("${loginHandle}")
    private int timeout;
    @Override
    public Message handle(Message message) {
        UserMapper mapper = server.getUserMapper();
        User user = new User(server,message.getOriginator(),message.getData().get("password").toString());
        String password = server.getRegisterUsers().get(user.getAccount());
        User temp = password == null ?  mapper.select(user) : new User(server,user.getAccount(),password);
        HashMap<String,Object> data = new HashMap<>();
        UserManager tempUser = server.getTempUser();

        if (temp != null && temp.getPassword().equals(user.getPassword())){
            data.put("code","200");
            data.put("message","登录成功！");

            StringBuilder builder = new StringBuilder();
            builder.append(user.hashCode()).append(server.getServiceName()).append(user).append(message.getType());
            data.put("key",MessageUtil.getMD5(builder.toString()));

            try {
                tempUser.deleteUser(user.getAccount());
                server.getLoginUser().deleteUser(user.getAccount());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            data.put("code","418");
            data.put("message","账户或密码错误");
        }

        //获取sendManager
        ApplicationContext context = server.getContext();
        SendManager sendManager = context.getBean("sendManager", SendManager.class);
        try {
            sendManager.init(server.getReceiveManager().getAccept());
            user.setSendManager(sendManager);
            tempUser.addUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Message replyMessage = MessageUtil.replyMessage(server.getServiceName(),message.getOriginator(),
                message.getID(), "login", data,true);
        if (data.size() > 2){
            UserAction action = new UserAction(replyMessage,timeout);
            server.getUserActionManager().add(action);
        }
        return replyMessage;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public LoginHandle() {
    }

    public LoginHandle(Server server, int timeout) {
        this.server = server;
        this.timeout = timeout;
    }
}
