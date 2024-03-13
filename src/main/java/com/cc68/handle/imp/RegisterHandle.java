package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.manager.ActionManager;
import com.cc68.manager.SendManager;
import com.cc68.manager.UserManager;
import com.cc68.mapper.MessageMapper;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.HashMap;
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Service("registerHandle")
public class RegisterHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Override
    public Message handle(Message message) {
        DefaultTransactionDefinition dt = new DefaultTransactionDefinition();
        dt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = platformTransactionManager.getTransaction(dt);
        HashMap<String, Object> data = message.getData();
        String account = data.get("account").toString();
        String password = data.get("password").toString();

        UserMapper mapper = server.getUserMapper();
        User user = new User(server,account,password);

        HashMap<String,Object> temp = new HashMap<>();
        if (mapper.select(user) == null){
            mapper.insert(user);
            temp.put("code","200");
            temp.put("message","注册成功");
            MessageMapper messageMapper = server.getMessageMapper();
            messageMapper.createTable(user.getAccount());
            platformTransactionManager.commit(status);
            ActionManager actionManager = server.getActionManager();
            actionManager.action("userUpdateBroadcast","register",user.getAccount());
        }else {
            temp.put("code","418");
            temp.put("message","注册失败");
        }

        UserManager tempUser = server.getTempUser();

        //获取sendManager
        ApplicationContext context = server.getContext();
        SendManager sendManager = context.getBean("sendManager", SendManager.class);
        try {
            sendManager.init(server.getReceiveManager().getAccept());
            user.setSendManager(sendManager);
            tempUser.addUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return MessageUtil.replyMessage(server.getServiceName(),account,"register",temp,true);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public RegisterHandle() {
    }

    public RegisterHandle(Server server) {
        this.server = server;
    }
}
