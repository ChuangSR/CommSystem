package com.cc68.heartbeat;
/**
 * 此接口的实现类用于摧毁心跳超时的用户
 * 预计被集成在HeartbeatManger中
 * */
public interface HeartbeatWrecker extends Runnable{
    /**
     * 关闭
     * */
    void close();
}
