package com.cc68.timeout.imp;

import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pojo.UserAction;
import com.cc68.service.Server;
import com.cc68.timeout.Timeout;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service("loginTimeout")
public class LoginTimeout implements Timeout {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Override
    public Message handle(UserAction action) {
        String receiver = action.getMessage().getReceiver();
        HashMap<String,Object> data = new HashMap<>();
        data.put("code","408");
        data.put("行为",action.getMessage().getType());
        return MessageUtil.replyMessage(server.getServiceName(), receiver, "connectTimeout", data, false);
    }
}
