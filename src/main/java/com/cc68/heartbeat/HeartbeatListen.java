package com.cc68.heartbeat;
/**
 * 心跳监听器
 * 预计被集成在HeartbeatManger中
 * */
public interface HeartbeatListen extends Runnable{
    /**
     * 关闭
     * */
    void close();
}
