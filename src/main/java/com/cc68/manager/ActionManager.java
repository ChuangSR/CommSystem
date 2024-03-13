package com.cc68.manager;

import com.cc68.pojo.Message;

/**
 * 用于管理服务器的行为操作
 * */
public interface ActionManager {
    Message action(String type, Object... data);
}
