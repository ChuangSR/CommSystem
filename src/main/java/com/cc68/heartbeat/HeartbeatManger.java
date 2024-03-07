package com.cc68.heartbeat;
/**
 * 用于管理登录客户端的心跳
 * 在超时之后，会将客户端下线
 * */
public interface HeartbeatManger{
    /**
     * 启动心跳管理器
     * */
    void start();
    /**
     * 关闭心跳管理器
     * */
    void close();
}
