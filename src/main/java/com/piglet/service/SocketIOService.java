package com.piglet.service;

import com.piglet.domain.PushMessage;

public interface SocketIOService {

    //推送的事件
    public static final String PUSH_EVENT = "push_event";

    public static final String ON_LINE = "on_line";

    public static final String OFF_LINE = "off_line";

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息(个人)
    void pushMessageToUser(PushMessage pushMessage);

    // 推送信息(所有)
    void pushMessageToAll(String event,PushMessage pushMessage);
}
