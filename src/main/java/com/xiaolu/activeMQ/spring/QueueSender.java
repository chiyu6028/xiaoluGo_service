package com.xiaolu.activeMQ.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

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
}
