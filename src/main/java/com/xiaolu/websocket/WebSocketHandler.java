package com.xiaolu.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.*;

/**
 * 基于spring4的websocket 方式二
 * Created by chinaD on 2018/1/5.
 */
public class WebSocketHandler extends AbstractWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private static int count = 0;
    private Map<String,WebSocketSession> wssMap = new HashMap<String,WebSocketSession>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //super.afterConnectionEstablished(session);
        logger.debug("连接成功");
        count ++;
        wssMap.put((String) session.getAttributes().get("user_id"),session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //super.handleTextMessage(session, message);
        String msg = message.getPayload();
        logger.debug("收到消息："+msg);
        //休息2s后再发消息出去
        Thread.sleep(2000);
        Map attrMap = session.getAttributes();

        Iterator iterator = wssMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
            WebSocketSession isession = entry.getValue();
            String userId = entry.getKey();
            isession.sendMessage(new TextMessage( attrMap.get("user_id")+"说：欢迎你！"+ userId +"。当前人数："+count ));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //super.handleTransportError(session, exception);
        logger.debug("连接错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //super.afterConnectionClosed(session, status);
        logger.debug("连接关闭");
        count--;
        wssMap.remove((String)session.getAttributes().get("user_id"));
    }
}
