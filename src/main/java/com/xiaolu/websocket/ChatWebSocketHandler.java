package com.xiaolu.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * Created by chinaD on 2017/11/15.
 */
@ServerEndpoint(value = "/chat/{user_id}", configurator = SpringConfigurator.class)
public class ChatWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

    //创建连接容器
    private static Map<String,ChatWebSocketHandler> userMap = new HashMap<>();

    //连接用户Id
    private String user_id;

    //连接用户Session
    private Session userSession;

    //当前登录人数
    private static int count = 0;

    //获取连接时
    @OnOpen
    public void onOpen(@PathParam("user_id") String user_id, Session session) throws IOException {
        logger.info("slfj:连接成功");
        this.user_id = user_id;
        this.userSession = session;

        if (userMap.containsKey(user_id)){
            logger.info("当前用户已经在其他客户端登录");
            userMap.put(user_id,this);
        } else {
            logger.info("当前用户第一次在客户端登录");
            count ++;
            userMap.put(user_id,this);
        }

        session.getBasicRemote().sendText("欢迎用户："+user_id+",当前登录人数为：" + count);

    }

    //关闭连接时
    @OnClose
    public void onClose() {
        logger.info("连接关闭");
    }

    //获取信息时
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("收到用户:{" + this.user_id + "}信息:{" + message + "}");
        //session.getBasicRemote().sendText("用户:" + this.user_id + "的消息是：" + message);

        if (message.contains("@")){
            String[] msgArray = message.split("：");
            String toUser_id = null;
            String toMessage = null;
            for (String str : msgArray){
                if (str.contains("@")){
                    toUser_id = str.split("@")[1];
                }
            }
            toMessage = msgArray[1].trim();
            if (userMap.get(toUser_id) != null){
                ChatWebSocketHandler chat = userMap.get(toUser_id);
                chat.userSession.getBasicRemote().sendText("用户:" + this.user_id + "的消息是：" + toMessage);
            }else{
                this.userSession.getBasicRemote().sendText("消息发送失败");
            }
        }else {
            for (String key : userMap.keySet()){
                ChatWebSocketHandler chat = userMap.get(key);
                chat.userSession.getBasicRemote().sendText("用户:" + this.user_id + "的消息是：" + message);
            }
        }

    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("用户id为:{" + this.user_id + "}的连接发送错误");
        error.printStackTrace();
    }

    public Boolean sendMassage() {
        System.out.println(this.user_id);

        return true;
    }

}
