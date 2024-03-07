package com.cc68.heartbeat.imp;

import com.cc68.heartbeat.HeartbeatListen;
import com.cc68.heartbeat.HeartbeatManger;
import com.cc68.heartbeat.HeartbeatWrecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("heartbeatManger")
public class HeartbeatMangerImp implements HeartbeatManger {
    @Autowired
    @Qualifier("heartbeatListen")
    private HeartbeatListen listen;

    @Autowired
    @Qualifier("heartbeatWrecker")
    private HeartbeatWrecker wrecker;
    @Override
    public void start() {
        Thread listenThead = new Thread(listen);
        Thread wreckerThead = new Thread(wrecker);
        listenThead.start();
        wreckerThead.start();
    }

    @Override
    public void close() {
        listen.close();
        wrecker.close();
    }

    public HeartbeatListen getListen() {
        return listen;
    }

    public void setListen(HeartbeatListen listen) {
        this.listen = listen;
    }

    public HeartbeatWrecker getWrecker() {
        return wrecker;
    }

    public void setWrecker(HeartbeatWrecker wrecker) {
        this.wrecker = wrecker;
    }

    public HeartbeatMangerImp() {
    }

    public HeartbeatMangerImp(HeartbeatListen listen, HeartbeatWrecker wrecker) {
        this.listen = listen;
        this.wrecker = wrecker;
    }
}
