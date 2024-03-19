package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.manager.MessageHandleManager;
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

@Service("unregisterHandle")
public class UnregisterHandle implements Handle {
    @Autowired
    private Server server;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Override
    public Message handle(Message message) {
        DefaultTransactionDefinition dt = new DefaultTransactionDefinition();
        dt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = platformTransactionManager.getTransaction(dt);

        UserMapper mapper = server.getUserMapper();
        HashMap<String,Object> temp = new HashMap<>();
        String originator = message.getOriginator();
        try {
            mapper.delete(originator);
            platformTransactionManager.commit(status);
            temp.put("code","200");
            temp.put("message","注销成功");
            MessageHandleManager messageHandleManager = server.getMessageHandleManager();
            message.setType("logout");
            messageHandleManager.handle(message);

            server.getRegisterUsers().put(originator,"-1");
        }catch (Exception e){
            temp.put("code","418");
            temp.put("message","注销失败");
        }
        return null;
    }
}
