package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.manager.ActionManager;
import com.cc68.pojo.Message;
import com.cc68.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("logoutHandle")
public class LogoutHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Override
    public Message handle(Message message) {
        String originator = message.getOriginator();
        try {
            server.getLoginUser().deleteUser(originator);
        } catch (IOException e) {
        }
        ActionManager actionManager = server.getActionManager();
        actionManager.action("userUpdateBroadcast","logout",message.getOriginator());
        return null;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public LogoutHandle() {
    }

    public LogoutHandle(Server server) {
        this.server = server;
    }
}
