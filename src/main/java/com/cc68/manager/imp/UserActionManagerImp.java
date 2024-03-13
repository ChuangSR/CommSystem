package com.cc68.manager.imp;

import com.cc68.manager.UserActionManager;
import com.cc68.manager.UserManager;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pojo.UserAction;
import com.cc68.service.Server;
import com.cc68.timeout.Timeout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class UserActionManagerImp implements UserActionManager {
    private Server server;
    private ArrayList<UserAction> list = new ArrayList<>(100);

    private UserManager tempManager;

    private Map<String, Timeout> map;

    private int timeout;

    private boolean flag = true;
    public UserActionManagerImp() {
    }
    public void init(){
        Thread thread = new Thread(this);
        thread.start();
        tempManager = server.getTempUser();
    }
    @Override
    public void run() {
        while (flag){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ArrayList<UserAction> temp = new ArrayList<>();
            for (UserAction action:list){
                if (action.getTimeout() < System.currentTimeMillis() - action.getTime()){
                    Message reply = map.get(action.getMessage().getType()).handle(action);
                    if (reply!=null){
                        User user = server.getUser(reply.getReceiver());
                        if (user!=null){
                            try {
                                user.getSendManager().send(reply);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    temp.add(action);
                }
            }
            list.removeAll(temp);

            ArrayList<User> users = tempManager.getAll();
            ArrayList<User> tempUsers = new ArrayList<>();

            for (User user:users){
                if (System.currentTimeMillis()/1000 - user.getLinkTime() > timeout){
                    tempUsers.add(user);
                }
            }
            try {
                tempManager.deleteUser(tempUsers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(UserAction action) {
        list.add(action);
    }
    public UserAction get(String key){
        for (UserAction action:list){
            if (action.getMessage().getData().get("key").equals(key)) {
                return action;
            }
        }
        return null;
    }
    public void delete(UserAction action){
        list.remove(action);
    }
    public void close(){
        for (UserAction action:list){
            map.get(action.getMessage().getType()).handle(action);
            list.remove(action);
        }
        flag = false;
    }

    public ArrayList<UserAction> getList() {
        return list;
    }

    public void setList(ArrayList<UserAction> list) {
        this.list = list;
    }

    public Map<String, Timeout> getMap() {
        return map;
    }

    public void setMap(Map<String, Timeout> map) {
        this.map = map;
    }

    public boolean isFlag() {
        return flag;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public UserManager getTempManager() {
        return tempManager;
    }

    public void setTempManager(UserManager tempManager) {
        this.tempManager = tempManager;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public UserActionManagerImp(Server server, ArrayList<UserAction> list,
                                UserManager tempManager, Map<String, Timeout> map, int timeout, boolean flag) {
        this.server = server;
        this.list = list;
        this.tempManager = tempManager;
        this.map = map;
        this.timeout = timeout;
        this.flag = flag;
    }
}
