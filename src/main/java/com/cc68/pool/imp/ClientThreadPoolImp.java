package com.cc68.pool.imp;

import com.cc68.pojo.User;
import com.cc68.pool.ClientThread;
import com.cc68.pool.ClientThreadPool;
import com.cc68.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
@Controller("clientThreadPool")
public class ClientThreadPoolImp implements ClientThreadPool {
    @Autowired
    @Qualifier("server")
    private Server server;
    /**
     * 线程池的最大数
     * */
    @Value("${poolMax}")
    private int MAX;
    /**
     * 用于存储线程
     * */
    private ArrayList<ClientThread> pool;
    /**
     * 超时时长
     * */
    @Value("${timeout}")
    private int timeout;

    private boolean flag = true;

    public ClientThreadPoolImp(){}


    public ClientThreadPoolImp(Server server,int MAX,int timeout) {
        this.server = server;
        this.MAX = MAX;
        this.timeout = timeout;
    }

    public void start(){
        this.pool = new ArrayList<>(MAX);
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 后台运行的线程，为了查看超时的线程
     */
    @Override
    public void run() {
        while (flag){
            try {
                Thread.sleep(1000);
                ClientThread temp =null;
                for (ClientThread thread:pool){
                    long linkTime = thread.getUser().getLinkTime();
                    long now = System.currentTimeMillis()/1000;
                    if (now - linkTime >= timeout){
                        closeThread(thread);
                        temp = thread;
                    }
                }
                if (temp!=null){
                    delete(temp);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 添加一个线程，如果在以满的情况下会关闭一个线程
     * @param socketThread 被添加的线程
     * @throws IOException
     */

    public void add(ClientThread socketThread) throws IOException {

        /**
         * 超出线程池容量的处理
         * 将一个登录发送消息时间最长的用户下线
         * */
        if (pool.size() == MAX){
            long MAX = 0;
            int index = 0;
            for (int i = 0;i < pool.size();i++){
                ClientThread temp = pool.get(i);
                long linkTime = temp.getUser().getLinkTime();
                long time = System.currentTimeMillis()/1000 - linkTime;
                if (time > timeout){
                    closeThread(temp);
                    pool.remove(temp);
                    pool.add(socketThread);
                    return;
                }else {
                    MAX = Math.max(MAX,time);
                    index = i;
                }
            }
            closeThread(pool.get(index));
            pool.remove(index);
            pool.add(socketThread);
        }else {
            pool.add(socketThread);
        }
    }

    public void delete(ClientThread thread) throws IOException {
        if (thread != null){
            thread.close();
            pool.remove(thread);
        }
    }

    @Override
    public void close() {
        flag = false;
    }

    /**
     * 循环查找要被关闭的线程
     * @throws IOException
     */
    private void checkThread() throws IOException {

    }

    /**
     * 负责关闭一个线程
     * @param thread 被关闭的线程
     * @throws IOException
     */
    private void closeThread(ClientThread thread) throws IOException {
        //获取bean对象
        User user = thread.getUser();
        if (user == null){
            thread.close();
            delete(thread);
            return;
        }

    }


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public ArrayList<ClientThread> getPool() {
        return pool;
    }

    public void setPool(ArrayList<ClientThread> pool) {
        this.pool = pool;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getSize(){
        return pool.size();
    }
}
