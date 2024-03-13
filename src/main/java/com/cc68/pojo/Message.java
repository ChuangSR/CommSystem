package com.cc68.pojo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 用于存储发出消息的类
 * 在之后会将数据转为json
 */
public class Message implements Serializable {
    //消息的id
    private String ID;
    //消息的发送者，为一个账号
    private String originator;
    //接收者
    private String receiver;
    //消息的类型
    private String type;
    //具体的消息
    private HashMap<String,Object> data;
    //此条消息是否需要回复
    private boolean reply;

    private long time;
    public Message(){

    }

    public Message(String ID, String originator, String type, HashMap<String, Object> data, boolean reply) {
        this.ID = ID;
        this.originator = originator;
        this.type = type;
        this.data = data;
        this.reply = reply;
        String s = String.valueOf(System.currentTimeMillis() / 10000);
        this.time = Long.parseLong(String.copyValueOf(s.toCharArray(),2,s.length() - 2));
    }

    public Message(String ID, String originator, String receiver, String type, HashMap<String, Object> data, boolean reply) {
        this.ID = ID;
        this.originator = originator;
        this.receiver = receiver;
        this.type = type;
        this.data = data;
        this.reply = reply;
        String s = String.valueOf(System.currentTimeMillis() / 10000);
        this.time = Long.parseLong(String.copyValueOf(s.toCharArray(),2,s.length() - 2));
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
