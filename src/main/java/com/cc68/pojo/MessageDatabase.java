package com.cc68.pojo;

import java.io.Serializable;

/**
 * 存储数据库数据的类
 */
public class MessageDatabase implements Serializable{
    private static final long serialVersionUID = 68686868L;

    private String ID;
    //发送者
    private String originator;
    //接收这
    private String receiver;
    //消息内容
    private String message;
    //消息类型
    private String type;
    //发送的时间
    private long time;

    private String position = "!";

    public MessageDatabase(){
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public MessageDatabase(String ID, String originator, String receiver, String message, String type) {
        this.ID = ID;
        this.originator = originator;
        this.receiver = receiver;
        this.message = message;
        this.type = type;
        this.time = System.currentTimeMillis();
    }
}
