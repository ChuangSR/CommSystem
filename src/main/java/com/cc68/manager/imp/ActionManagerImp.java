package com.cc68.manager.imp;

import com.cc68.action.Action;
import com.cc68.manager.ActionManager;
import com.cc68.pojo.Message;
import com.cc68.service.Server;

import java.util.Map;

public class ActionManagerImp implements ActionManager {
    private Server server;
    private Map<String, Action> action;
    @Override
    public Message action(String type, Object... data) {
        action.get(type).action(data);
        return null;
    }


    public Map<String, Action> getAction() {
        return action;
    }

    public void setAction(Map<String, Action> action) {
        this.action = action;
    }

    public ActionManagerImp() {
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
