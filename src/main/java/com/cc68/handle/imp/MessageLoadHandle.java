package com.cc68.handle.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.handle.Handle;
import com.cc68.mapper.MessageMapper;
import com.cc68.pojo.Message;
import com.cc68.pojo.MessageDatabase;
import com.cc68.service.Server;
import com.cc68.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
@Service("messageLoadHandle")
public class MessageLoadHandle implements Handle {
    @Autowired
    private Server server;
    @Override
    public Message handle(Message message) {
        HashMap<String, Object> data = message.getData();
        HashMap<String,Object> dataMap = new HashMap<>();
        MessageMapper messageMapper = server.getMessageMapper();
        ArrayList<MessageDatabase> messageDatabases = null;
        if (data!=null){
            String id = (String)data.get("ID");
            long time = Long.parseLong(data.get("time").toString());
            messageDatabases = messageMapper.messageLoad(message.getOriginator(),time);
            ArrayList<MessageDatabase> temp = new ArrayList<>();
            for (MessageDatabase messageDatabase:messageDatabases){
                temp.add(messageDatabase);
                if (messageDatabase.getID().equals(id)){
                    break;
                }
            }
            messageDatabases.removeAll(temp);
        }else {
            messageDatabases = messageMapper.messageLoad(message.getOriginator(),0);

        }
        dataMap.put("size",messageDatabases.size());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(messageDatabases);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        byte[] byteArray = baos.toByteArray();
        dataMap.put("messages",JSON.toJSONString(byteArray));
        return MessageUtil.replyMessage(server.getServiceName(),message.getOriginator(),"messageLoad",dataMap,true);
    }
//    private String build(byte[] bytes){
//        StringBuilder buffer = new StringBuilder();
//        for (byte i :bytes){
//            buffer.append(i);
//            buffer.append("|");
//        }
//        return buffer.toString();
//    }
}
