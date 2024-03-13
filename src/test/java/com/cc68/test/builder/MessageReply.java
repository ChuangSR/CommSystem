package com.cc68.test.builder;

import com.cc68.pojo.Message;

import java.util.HashMap;

public interface MessageReply {
    /**
     * 一个专门用于服务器返回消息的方法
     * serverName 服务器名称
     * ID 消息的ID
     * type 消息的类型
     * data 具体的数据
     * */
    Message replyMessage(String serverName, String ID, HashMap<String,String> data);
}
