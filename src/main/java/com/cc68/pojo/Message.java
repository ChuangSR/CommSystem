package com.cc68.pojo;

import java.util.HashMap;

/**
 * 用于存储发出消息的类
 * 在之后会将数据转为json
 */
public class Message {
    //消息的id
    private String ID;
    //消息的发送者，为一个账号
    private String originator;
    //消息的类型
    private String type;
    //具体的消息
    private HashMap<String,String> data;
    //此条消息是否需要回复
    private boolean reply;
    public Message(){

    }

    public Message(String ID, String originator, String type) {
        this.ID = ID;
        this.originator = originator;
        this.type = type;
    }

    public Message(String ID, String originator, String type, HashMap<String, String> data) {
        this.ID = ID;
        this.originator = originator;
        this.type = type;
        this.data = data;
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

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }
}
