package com.cc68.handle.imp;

import com.cc68.builder.MessageBuilder;
import com.cc68.handle.Handle;
import com.cc68.manager.MessageHandleManager;
import com.cc68.pojo.Message;
import com.cc68.service.Server;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller("loginHandle")
public class LoginHandle implements Handle {
    private Server server;
    private MessageHandleManager messageHandleManager;
    @Override
    public Message handle(Message message) {
        MessageBuilder builder = messageHandleManager.getBuilder();
        return null;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public MessageHandleManager getMessageHandleManager() {
        return messageHandleManager;
    }

    public void setMessageHandleManager(MessageHandleManager messageHandleManager) {
        this.messageHandleManager = messageHandleManager;
    }

    public LoginHandle() {
    }

    public LoginHandle(Server server, MessageHandleManager messageHandleManager) {
        this.server = server;
        this.messageHandleManager = messageHandleManager;
    }
}
