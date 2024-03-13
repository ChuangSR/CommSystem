package com.cc68.util;

import com.alibaba.fastjson2.JSON;
import com.cc68.pojo.Message;
import com.cc68.pojo.MessageDatabase;
import com.cc68.pojo.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MessageUtil {
    private MessageUtil(){}
    public static String getMD5(String input) {
        if(input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            StringBuilder result = new StringBuilder(bigInt.toString(16));
            // 不足32位高位补零
            while(result.length() < 32) {
                result.insert(0, "0");
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String getID(String type,String account){
        long timeMillis = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append(timeMillis).append(type).append(account);
        return getMD5(builder.toString());
    }

    public static String getTime() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static MessageDatabase toMessageDatabase(Message message){
        MessageDatabase messageDatabase = new MessageDatabase();
        messageDatabase.setID(message.getID());
        messageDatabase.setOriginator(message.getOriginator());
        messageDatabase.setReceiver(message.getReceiver());
        messageDatabase.setType(message.getType());
        messageDatabase.setMessage(message.getData().get("message").toString());
        messageDatabase.setTime(message.getTime());
        return messageDatabase;
    }
    public static Message buildMessage(String account, String type, HashMap<String, String> data) {
        return null;
    }

    public static Message replyMessage(String serverName,String receiver, String ID,String type, HashMap<String, Object> data,boolean reply) {
        return new Message(ID,serverName,receiver,type,data,reply);
    }

    public static Message replyMessage(String serverName,String receiver,String type, HashMap<String, Object> data,boolean reply) {
        return new Message(getID(type,receiver),serverName,receiver,type,data,reply);
    }
}
