package com.xiaolu.activeMQ.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by chinaD on 2017/11/23.
 */
@Component
public class QueueListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(QueueListener.class);
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            try {
                logger.debug(textMessage.getText());
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
