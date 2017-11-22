package com.xiaolu.activeMQ.queues;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by chinaD on 2017/11/22.
 */
public class MyConsumer {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MyConsumer.class);

        //1.创建工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            //3.创建Session
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //4.创建目标
            Destination destination = session.createQueue("myMQ");
            //5.创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            //6.创建消息监听
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        TextMessage msg = (TextMessage) message;
                        logger.debug(" consumer message :" + msg.getText());
                        session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
