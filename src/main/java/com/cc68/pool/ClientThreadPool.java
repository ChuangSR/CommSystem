package com.cc68.pool;

import java.io.IOException;

public interface ClientThreadPool extends Runnable{
    /**
     * 开启线程池
     * */
    void start();
    /**
     * 向线程池添加一个线程
     * */
    void add(ClientThread thread) throws IOException;
    /**
     * 删除一个客户端线程
     * */
    void delete(ClientThread thread) throws IOException;
    /**
     * 检查线程，如果客户端长时间未发送数据包，那么服务器会将其脱机
     * 但是不会在UserManager中删除该用户（除非该用户的心跳出现异常）
     * */
//    void checkThread() throws IOException;
    void close();
    int getSize();
}
