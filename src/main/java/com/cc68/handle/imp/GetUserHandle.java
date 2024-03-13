package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.manager.UserManager;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("getUserHandle")
public class GetUserHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Override
    public Message handle(Message message) {
        ArrayList<User> allUser = server.getUserMapper().selectAll();
        UserManager loginUser = server.getLoginUser();
        HashMap<String,Object> data = new HashMap<>();
        data.put("AllUsers",allUser.size());
        data.put("LoginUsers",loginUser.getAll().size());
        ArrayList<HashMap<String,String>> users = new ArrayList<>();
        for (User user:allUser){
            HashMap<String,String> temp = new HashMap<>();
            temp.put("account",user.getAccount());
            temp.put("status",String.valueOf(loginUser.getUser(user.getAccount())!=null));
            users.add(temp);
        }
        data.put("Users",users);
        return MessageUtil.replyMessage(server.getServiceName(),message.getOriginator(),"getUser",data,true);
    }
}
