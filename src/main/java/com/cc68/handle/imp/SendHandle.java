package com.cc68.handle.imp;

import com.cc68.handle.Handle;
import com.cc68.mapper.MessageMapper;
import com.cc68.pojo.Message;
import com.cc68.pojo.MessageDatabase;
import com.cc68.pojo.User;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;

@Service("sendHandle")
public class SendHandle implements Handle {
    @Autowired
    @Qualifier("server")
    private Server server;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Override
    public Message handle(Message message) {
        String receiver = message.getReceiver();
        User user = server.getLoginUser().getUser(receiver);
        if (user != null){
            try {
                user.getSendManager().send(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        DefaultTransactionDefinition dt = new DefaultTransactionDefinition();
        dt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = platformTransactionManager.getTransaction(dt);

        MessageDatabase messageDatabase = MessageUtil.toMessageDatabase(message);
        MessageMapper messageMapper = server.getMessageMapper();

        System.out.println(messageDatabase);

        messageMapper.insertOriginatorMessageDatabase(messageDatabase);
        messageMapper.insertReceiverMessageDatabase(messageDatabase);
        platformTransactionManager.commit(status);
        return null;
    }
}
