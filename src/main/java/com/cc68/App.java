package com.cc68;

import com.cc68.service.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        Thread thread = new Thread(server);
        thread.start();
    }
}
