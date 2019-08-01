package com.piglet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.piglet.domain.PushMessage;
import com.piglet.domain.User;
import com.piglet.service.SocketIOService;
import com.piglet.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {

    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private UserService userService;

    private User user;
    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception  {
        stop();
    }

    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                user = userService.findUserById(Integer.parseInt(loginUserNum));
                clientMap.put(loginUserNum, client);
                System.out.println(client.getRemoteAddress()+ "<--他来了他来了他来了");
                pushMessageToAll(ON_LINE,new PushMessage(user.getUserId(),user.getRealname(),user.getRealname()+"已经连接...",new Date()));
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String loginUserNum = getParamsByClient(client);
            if (loginUserNum != null) {
                System.out.println(client.getRemoteAddress()+ "<--他走了他走了他走了");
                pushMessageToAll(OFF_LINE,new PushMessage(user.getUserId(),user.getRealname(),user.getRealname()+"已经断开...",new Date()));
                clientMap.remove(loginUserNum);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener("sendMessage", JSONObject.class, (client, data, ackSender) -> {
            JSONObject jsonObject = data;
            String content = ((String)jsonObject.get("content")).replaceAll(" ","&nbsp;");
            String realname = (String)jsonObject.get("loginUserName");
            Integer userId = (Integer) jsonObject.get("loginUserNum");
            pushMessageToAll("sendMessage",new PushMessage(userId,realname,content,new Date()));
        });
        socketIOServer.start();
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToUser(PushMessage pushMessage) {
        String loginUserNum = pushMessage.getLoginUserNum()+"";
        if (StringUtils.isNotBlank(loginUserNum)) {
            SocketIOClient client = clientMap.get(loginUserNum);
            if (client != null)
                client.sendEvent(PUSH_EVENT, pushMessage);
        }
    }

    @Override
    public void pushMessageToAll(String event, PushMessage pushMessage) {
        Set<String> keySet = clientMap.keySet();
        for(String loginUserNum : keySet){
            SocketIOClient client = clientMap.get(loginUserNum);
            if (client != null)
                client.sendEvent(event, pushMessage);
        }
    }


    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("loginUserNum");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}