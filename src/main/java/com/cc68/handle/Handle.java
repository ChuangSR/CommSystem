package com.cc68.handle;

import com.cc68.pojo.Message;
import com.cc68.service.Server;

/**
 * 该接口的实例为消息的具体处理
 * 预计被集成在MessageHandleManager中
 * */
public interface Handle {
    Message handle(Message message);
}
