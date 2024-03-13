package com.cc68.pojo;
/**
 * 用于存储一些具有时限性的行为
 * 将被交于UserActionManager管理
 * 超时会被剔除
 * */
public class UserAction {
    /**
     * 此处的消息一般为服务器构建的，预计带有key
     * */
    private Message message;
    /**
     * 此条记录被创建的时间
     * 为一个毫秒数
     * */
    private long time;
    /**
     * 超时的时间，单位为毫秒
     * */
    private long timeout;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public UserAction() {
    }

    public UserAction(Message message, long timeout) {
        this.message = message;
        this.time = System.currentTimeMillis();
        this.timeout = timeout * 1000;
    }
}
