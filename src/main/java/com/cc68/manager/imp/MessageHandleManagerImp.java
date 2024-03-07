package com.cc68.manager.imp;

import com.cc68.builder.MessageBuilder;
import com.cc68.handle.Handle;
import com.cc68.manager.MessageHandleManager;
import com.cc68.manager.UserManager;
import com.cc68.pojo.Message;
import com.cc68.service.Server;

import java.util.HashMap;

public class MessageHandleManagerImp implements MessageHandleManager {
    private Server server;
    private MessageBuilder builder;

    private UserManager manager;

    private HashMap<String, Handle> service;
    @Override
    public Message handle(Message message) {
        return service.get(message.getType()).handle(message);
    }

    public MessageBuilder getBuilder() {
        return builder;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setBuilder(MessageBuilder builder) {
        this.builder = builder;
    }

    public HashMap<String, Handle> getService() {
        return service;
    }

    public void setService(HashMap<String, Handle> service) {
        this.service = service;
    }

    public UserManager getManager() {
        return manager;
    }

    public void setManager(UserManager manager) {
        this.manager = manager;
    }

    public MessageHandleManagerImp() {
    }

    public MessageHandleManagerImp(Server server, MessageBuilder builder, UserManager manager, HashMap<String, Handle> service) {
        this.server = server;
        this.builder = builder;
        this.manager = manager;
        this.service = service;
    }
}
