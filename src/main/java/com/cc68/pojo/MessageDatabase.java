package com.cc68.pojo;

/**
 * 存储数据库数据的类
 */
public class MessageDatabase {
    //发送者
    private String originator;
    //接收这
    private String receiver;
    //消息内容
    private String message;
    //消息类型
    private String type;
    //发送的时间
    private String time;

    public MessageDatabase(){}

    public MessageDatabase(String originator, String receiver, String message, String time, String type) {
        this.originator = originator;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
