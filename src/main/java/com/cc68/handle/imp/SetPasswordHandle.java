package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.Message;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
@Service("setPasswordHandle")
public class SetPasswordHandle implements Handle {
    @Autowired
    private Server server;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    public Message handle(Message message) {
        DefaultTransactionDefinition dt = new DefaultTransactionDefinition();
        dt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = platformTransactionManager.getTransaction(dt);

        HashMap<String,Object> temp = new HashMap<>();
        String originator = message.getOriginator();
        try {
            HashMap<String, Object> data = message.getData();
            String password = (String) data.get("password");
            UserMapper mapper = server.getUserMapper();
            mapper.update(originator,password);
            platformTransactionManager.commit(status);


            temp.put("code","200");
            temp.put("message","修改成功");
            server.getRegisterUsers().put(originator,password);
        }catch (Exception e){
            temp.put("code","418");
            temp.put("message","修改失败");
        }
        return MessageUtil.replyMessage(server.getServiceName(),originator,"setPassword",temp,true);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    public SetPasswordHandle() {
    }

    public SetPasswordHandle(Server server, PlatformTransactionManager platformTransactionManager) {
        this.server = server;
        this.platformTransactionManager = platformTransactionManager;
    }
}
