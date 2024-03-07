package com.cc68.pojo;

import com.cc68.manager.SendManager;
import com.cc68.manager.imp.SendManagerImp;
import com.cc68.pool.ClientThread;

import java.io.IOException;

/**
 * 一个用户的类，用于存储用户的一些信息和执行一些操作
 */
public class User {
    private String account;
    private String password;

    private SendManager sendManager;

    private long linkTime;

    private ClientThread thread;
    public User(){}

    public User(String account, String password) {
        this.account = account;
        this.password = password;
        linkTime = System.currentTimeMillis()/1000;
    }

    public SendManager getSendManager() {
        return sendManager;
    }

    public void setSendManager(SendManager sendManager) {
        this.sendManager = sendManager;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void close() throws IOException {
        sendManager.close();
    }

    public long getLinkTime() {
        return linkTime;
    }

    public ClientThread getThread() {
        return thread;
    }

    public void setThread(ClientThread thread) {
        this.thread = thread;
    }

    /**
     * 刷新用户状态
     * */
    public void refresh(){
        linkTime = System.currentTimeMillis()/1000;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
