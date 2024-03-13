package com.cc68;

import com.cc68.heartbeat.HeartbeatManger;
import com.cc68.heartbeat.imp.HeartbeatListenImp;
import com.cc68.service.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
        Server server = context.getBean("server", Server.class);
        server.init();
//        server.setContext(context);
        Thread thread = new Thread(server);
        thread.start();

//        Thread.sleep(10000);
//        server.close();
    }
}
