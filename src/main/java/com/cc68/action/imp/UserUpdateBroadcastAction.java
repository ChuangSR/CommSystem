package com.cc68.action.imp;

import com.cc68.action.Action;
import com.cc68.manager.UserManager;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service("userUpdateBroadcastAction")
public class UserUpdateBroadcastAction implements Action {
    @Autowired
    private Server server;

    @Override
    public void action(Object... o) {
        String type = (String) o[0];
        String account = (String) o[1];
        UserManager loginUser = server.getLoginUser();
        HashMap<String,Object> temp = new HashMap<>();
        temp.put("account",account);
        temp.put("type",type);
        Message replyMessage = MessageUtil.replyMessage(server.getServiceName(), "all", "userUpdateBroadcast", temp, false);
        ArrayList<User> users = loginUser.getAll();
        for (User user:users){
            try {
                user.getSendManager().send(replyMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
