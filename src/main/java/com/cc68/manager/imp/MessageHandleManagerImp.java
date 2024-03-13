package com.cc68.manager.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.handle.Handle;
import com.cc68.manager.MessageHandleManager;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.Message;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

public class MessageHandleManagerImp implements MessageHandleManager {

    private Server server;

    private UserMapper userMapper;

    private Map<String, Handle> service;
    public void init(){
        userMapper = server.getUserMapper();
    }
    @Override
    public Message handle(Message message) {
        StringBuilder builder = new StringBuilder();
        builder.append("用户:").append(message.getOriginator()).
                append("\n行为:").append(message.getType()).
                append("\n消息:").append(JSON.toJSONString(message.getData())).
                append("\n时间:").append(MessageUtil.getTime());
        System.out.println(builder);
        return service.get(message.getType()).handle(message);
    }


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Map<String, Handle> getService() {
        return service;
    }

    public void setService(Map<String, Handle> service) {
        this.service = service;}

    public MessageHandleManagerImp() {
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public MessageHandleManagerImp(Server server, HashMap<String, Handle> service) {
        this.server = server;
        this.service = service;
    }
}
