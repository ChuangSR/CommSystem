package com.cc68.manager;

import com.cc68.builder.MessageBuilder;
import com.cc68.pojo.Message;
/**
 * 消息的逻辑处理管理器
 * */
public interface MessageHandleManager {
    /**
     * 消息的处理
     * */
    Message handle(Message message);

    MessageBuilder getBuilder();

    UserManager getManager();
}
