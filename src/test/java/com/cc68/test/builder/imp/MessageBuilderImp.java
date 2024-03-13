//package com.cc68.test.builder.imp;
//
//import com.cc68.test.builder.MessageBuild;
//import com.cc68.pojo.Message;
//import com.cc68.pojo.MessageDatabase;
//import com.cc68.pojo.User;
//import org.springframework.stereotype.Component;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//@Component("messageBuilder")
//public class MessageBuilderImp implements MessageBuild {
////    private LogMapper logMapper;
//    @Override
//    public Message buildMessage(String account, String type, HashMap<String, String> data) {
//        return null;
//    }
//
//    @Override
//    public Message replyMessage(String serverName, String ID, HashMap<String, String> data) {
//        return null;
//    }
//
//    @Override
//    public String getMD5(String input) {
//        if(input == null || input.length() == 0) {
//            return null;
//        }
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            md5.update(input.getBytes());
//            byte[] byteArray = md5.digest();
//
//            BigInteger bigInt = new BigInteger(1, byteArray);
//            // 参数16表示16进制
//            StringBuilder result = new StringBuilder(bigInt.toString(16));
//            // 不足32位高位补零
//            while(result.length() < 32) {
//                result.insert(0, "0");
//            }
//            return result.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private String getID(String type,String account){
//        long timeMillis = System.currentTimeMillis();
//        StringBuilder builder = new StringBuilder();
//        builder.append(timeMillis).append(type).append(account);
//        return getMD5(builder.toString());
//    }
//
//    @Override
//    public String getTime() {
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//        Date date = new Date(System.currentTimeMillis());
//        return formatter.format(date);
//    }
//
//    @Override
//    public void saveMessage(Message bean, User user) {
//        toMessageDatabase(bean,user.getAccount());
//    }
//
//    private MessageDatabase toMessageDatabase(Message message, String account){
//        MessageDatabase messageDatabase = new MessageDatabase();
//        messageDatabase.setOriginator(message.getOriginator());
//        messageDatabase.setReceiver(account);
//        messageDatabase.setType(message.getType());
//        messageDatabase.setMessage(message.getData().get("message"));
//        return messageDatabase;
//    }
//}
