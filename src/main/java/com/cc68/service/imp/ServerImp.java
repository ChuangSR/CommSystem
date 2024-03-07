package com.cc68.service.imp;

import com.cc68.heartbeat.HeartbeatManger;
import com.cc68.manager.MessageHandleManager;
import com.cc68.manager.ReceiveManager;
import com.cc68.manager.UserManager;
import com.cc68.manager.imp.ReceiveManagerImp;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pool.ClientThreadPool;
import com.cc68.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional
@Component("server")
public class ServerImp implements Server {
    /**
     * 服务器名称
     * */
    @Value("${serverName}")
    private String serviceName;
    private boolean flag = true;
    /**
     * 服务器接收器
     * */
//    @Autowired
    private ReceiveManager receiveManager;
    /**
     * 登录的用户
     * */
    private UserManager loginUser;
    /**
     * 非登录事件产生的用户
     * */
    private UserManager tempUser;
    /**
     * 心跳管理器
     * */
    private HeartbeatManger heartbeatManger;
    /**
     * 消息处理器，消息构造器将被集成在其中
     * */
    private MessageHandleManager messageHandleManager;
    /**
     * 线程池
     * */
    private ClientThreadPool pool;
    @Override
    public void init() {

    }

    @Override
    public void start() {
        while (flag){
            Message message = receiveManager.listen();
            Message reply = messageHandleManager.handle(message);
            if (reply.isReply()){
                User user = loginUser.getUser(reply.getOriginator());
                try {
                    user.getSendManager().send(reply);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
//            String originator = message.getOriginator();
//            String messageType = message.getType();
//
//            switch (messageType){
//                case "online":
//            }

//            if ("online".equals(messageBean.getType())){
//                userBean = usersManager.getUser(messageBean.getOriginator());
//                MessageBean replyBean = HandleMessage.handle(messageBean,userBean, this);
//                userBean.getSendManager().send(replyBean);
//                continue;
//            }
//
//            //判读是否为登录事件，改事件较为特殊
//            if ("login".equals(messageBean.getType()) || "logon".equals(messageBean.getType())
//                    ||"changPwd".equals(messageBean.getType())){
//                HashMap<String, String> data = messageBean.getData();
//                userBean = new UserBean(data.get("account"),data.get("password"));
//                userBean.setSocket(receiveManager.getAccept());
//            }else {
//                userBean = usersManager.getUser(messageBean.getOriginator());
//            }
//            MessageBean replyBean = HandleMessage.handle(messageBean, userBean,this);
//            ConsoleMessageManger.send(HandleMessage.getLog());
//
//            boolean flag = Boolean.parseBoolean(replyBean.getData().get("flag"));
//            if (flag){
//                userBean.getSendManager().send(replyBean);
//            }
//
//            if ("logon".equals(messageBean.getType())||"changPwd".equals(messageBean.getType())
//                    || ("login".equals(replyBean.getType())&&"400".equals(replyBean.getData().get("status")))){
//                SocketThread thread = pool.getThread(userBean);
//                userBean.close();
//                if (thread != null){
//                    thread.close();
//                }
//            }
        }
    }

    @Override
    public void close() {

    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

//    public ReceiveManagerImp getReceiveManager() {
//        return receiveManager;
//    }

    public void setReceiveManager(ReceiveManagerImp receiveManager) {
        this.receiveManager = receiveManager;
    }

    @Override
    public UserManager getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserManager loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public UserManager getTempUser() {
        return tempUser;
    }

    public void setTempUser(UserManager tempUser) {
        this.tempUser = tempUser;
    }

    public HeartbeatManger getHeartbeatManger() {
        return heartbeatManger;
    }

    public void setHeartbeatManger(HeartbeatManger heartbeatManger) {
        this.heartbeatManger = heartbeatManger;
    }

    public MessageHandleManager getMessageHandleManager() {
        return messageHandleManager;
    }

    public void setMessageHandleManager(MessageHandleManager messageHandleManager) {
        this.messageHandleManager = messageHandleManager;
    }

    public ClientThreadPool getPool() {
        return pool;
    }

    public void setPool(ClientThreadPool pool) {
        this.pool = pool;
    }
}
