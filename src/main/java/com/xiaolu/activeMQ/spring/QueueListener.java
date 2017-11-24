package com.xiaolu.activeMQ.spring;

import com.xiaolu.service.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by chinaD on 2017/11/23.
 */
@Component
public class QueueListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    WebsocketService websocketService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            try {
                String msg = textMessage.getText();
                logger.debug(msg);
                websocketService.sendMassage("activeMQ: "+ msg);
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        }
        if (message instanceof MapMessage){
            final MapMessage map = (MapMessage) message;
            try {
                logger.debug(map.toString());
                logger.debug(map.getString("content"));
                websocketService.sendMassage("activeMQ map: "+ map.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
