package com.cc68.service.imp;

import com.alibaba.fastjson2.JSON;
import com.cc68.heartbeat.HeartbeatManger;
import com.cc68.manager.*;
import com.cc68.manager.imp.ReceiveManagerServer;
import com.cc68.mapper.MessageMapper;
import com.cc68.mapper.UserMapper;
import com.cc68.pojo.Message;
import com.cc68.pojo.User;
import com.cc68.pool.ClientThreadPool;
import com.cc68.service.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

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
    @Autowired
    @Qualifier("receiveManagerServer")
    private ReceiveManager receiveManager;
    /**
     * 登录的用户
     * */
    @Autowired
    @Qualifier("userManager")
    private UserManager loginUser;
    /**
     * 非登录事件产生的用户
     * */
    @Autowired
    @Qualifier("userManager")
    private UserManager tempUser;
    /**
     * 心跳管理器
     * */
    @Autowired
    @Qualifier("heartbeatManger")
    private HeartbeatManger heartbeatManger;
    /**
     * 消息处理器，消息构造器将被集成在其中
     * */
    @Autowired
    @Qualifier("messageHandleManager")
    private MessageHandleManager messageHandleManager;
    /**
     * 线程池
     * */
    @Autowired
    @Qualifier("clientThreadPool")
    private ClientThreadPool pool;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    @Qualifier("userActionManager")
    private UserActionManager userActionManager;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    @Qualifier("actionManager")
    private ActionManager actionManager;

    private HashMap<String,String> registerUsers = new HashMap<>();
    @Override
    public void init() throws IOException {
        userActionManager.init();
        receiveManager.init();
        heartbeatManger.start();
        pool.start();
    }

    @Override
    public void run() {
        while (flag){
            Message message = receiveManager.listen();
            if (message!=null){
                Message reply = messageHandleManager.handle(message);
                if (reply!=null&&reply.isReply()){
                    User user = getUser(reply.getReceiver());
                    if (user!=null){
                        try {
                            user.getSendManager().send(reply);
                        } catch (IOException e) {
                            try {
                                loginUser.deleteUser(user);
                                tempUser.deleteUser(user);
                            } catch (IOException ex) {
                                ex.printStackTrace();
//                                throw new RuntimeException(ex);
                            }
                            e.printStackTrace();
//                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        flag = false;
        loginUser.close();
        tempUser.close();
        receiveManager.close();
        heartbeatManger.close();
        pool.close();
        ((ClassPathXmlApplicationContext)context).close();
        System.exit(0);
    }

    public User getUser(String account){
        User user = loginUser.getUser(account);
        return user != null ? user:tempUser.getUser(account);
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ReceiveManager getReceiveManager() {
        return receiveManager;
    }

    public void setReceiveManager(ReceiveManager receiveManager) {
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

    @Override
    public MessageHandleManager getMessageHandleManager() {
        return messageHandleManager;
    }

    public void setMessageHandleManager(MessageHandleManager messageHandleManager) {
        this.messageHandleManager = messageHandleManager;
    }

    @Override
    public ClientThreadPool getPool() {
        return pool;
    }

    public void setPool(ClientThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public UserActionManager getUserActionManager() {
        return userActionManager;
    }

    public void setUserActionManager(UserActionManager userActionManager) {
        this.userActionManager = userActionManager;
    }

    public MessageMapper getMessageMapper() {
        return messageMapper;
    }

    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public ServerImp() {
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public HashMap<String, String> getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(HashMap<String, String> registerUsers) {
        this.registerUsers = registerUsers;
    }

    public ServerImp(String serviceName, boolean flag, ReceiveManager receiveManager, UserManager loginUser,
                     UserManager tempUser, HeartbeatManger heartbeatManger, MessageHandleManager messageHandleManager,
                     ClientThreadPool pool, UserMapper userMapper,
                     UserActionManager userActionManager, ApplicationContext context,
                     MessageMapper messageMapper, ActionManager actionManager) {
        this.serviceName = serviceName;
        this.flag = flag;
        this.receiveManager = receiveManager;
        this.loginUser = loginUser;
        this.tempUser = tempUser;
        this.heartbeatManger = heartbeatManger;
        this.messageHandleManager = messageHandleManager;
        this.pool = pool;
        this.userMapper = userMapper;
        this.userActionManager = userActionManager;
        this.context = context;
        this.messageMapper = messageMapper;
        this.actionManager = actionManager;
    }
}
