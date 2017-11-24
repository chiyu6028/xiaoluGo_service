package com.xiaolu.service.impl;

import com.xiaolu.service.WebsocketService;
import com.xiaolu.websocket.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chinaD on 2017/11/16.
 */
@Service("webSocketService")
public class WebSocketServiceImpl implements WebsocketService {

    @Autowired
    ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public Boolean sendMassage(String msg) {

        Boolean b = chatWebSocketHandler.sendMassage(msg);
        if (b){
            return true;
        }else {
            return false;
        }

    }
}
