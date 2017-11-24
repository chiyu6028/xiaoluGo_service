package com.xiaolu.activeMQ.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/23.
 */
@Component
public class QueueSender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void send(String message){
        jmsTemplate.convertAndSend("Queue.One",message);
    }

    public void sendTwo(String message){
        jmsTemplate.convertAndSend("Queue.Two",message);
    }

    public void sendMap (Map map){
        jmsTemplate.convertAndSend("Queue.Three", map, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setIntProperty("AccountID", 1234);
                message.setJMSCorrelationID("123-00001");
                return message;
            }
        });
    }
}
