package com.xiaolu.service.impl;

import com.xiaolu.service.WebsocketService;
import com.xiaolu.websocket.ChatWebSocketHandler;

/**
 * Created by chinaD on 2017/11/16.
 */
public class WebSocketServiceImpl implements WebsocketService {

    ChatWebSocketHandler chatWebSocketHandler;

    public void setChatWebSocketHandler(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @Override
    public Boolean sendMassage() {

        if (chatWebSocketHandler.sendMassage()){
            return true;
        }else {
            return false;
        }

    }
}
