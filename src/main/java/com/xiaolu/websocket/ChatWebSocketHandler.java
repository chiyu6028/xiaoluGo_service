package com.xiaolu.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by chinaD on 2017/11/15.
 */
@ServerEndpoint(value = "/chat/{user_id}", configurator = SpringConfigurator.class)
public class ChatWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    private static String user_id;

    //获取连接时
    @OnOpen
    public void onOpen(@PathParam("user_id") String user_id, Session session) {
        this.user_id = user_id;
        System.out.println("onOpen 连接:" + user_id);
    }

    //关闭连接时
    @OnClose
    public void onClose() {
        System.out.println("连接关闭");
    }

    //获取信息时
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到用户:{" + this.user_id + "}信息:{" + message + "}");
        session.getBasicRemote().sendText("用户:" + this.user_id + "的消息是：" + message);
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("用户id为:{" + this.user_id + "}的连接发送错误");
        error.printStackTrace();
    }

    public Boolean sendMassage() {
        return true;
    }

}
