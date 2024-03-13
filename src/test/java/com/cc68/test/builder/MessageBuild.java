package com.cc68.test.builder;

import com.cc68.pojo.Message;
import com.cc68.pojo.User;

import java.util.HashMap;
//MessageReply
public interface MessageBuild {
    /**
     * 构建一个需要发送的消息
     * account为发送者，在服务器调用的情况下，为服务器的名称
     * type为消息的类型
     * data为消息的具体数据
     * */
    Message buildMessage(String account, String type, HashMap<String,String> data);
    /**
     * 一个专门用于服务器返回消息的方法
     * serverName 服务器名称
     * ID 消息的ID
     * type 消息的类型
     * data 具体的数据
     * */
    Message replyMessage(String serverName,String ID, HashMap<String,String> data);
    /**
     * 根据输入的值获取一个md5
     * */
    String getMD5(String input);
    /**
     * 获取一个时间戳格式为:yyyy-MM-dd 'at' HH:mm:ss z
     * */
    String getTime();
    /**
     * 将日志存储到数据库
     * User为接收数据的对象
     * */
    void saveMessage(Message bean, User user);
}
