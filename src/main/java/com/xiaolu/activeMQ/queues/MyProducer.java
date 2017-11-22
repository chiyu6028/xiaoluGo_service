package com.xiaolu.activeMQ.queues;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by chinaD on 2017/11/22.
 */
public class MyProducer {


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MyProducer.class);
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = null;
        Session session = null;
        try {
            //2.创建连接
            connection = connectionFactory.createConnection();
            connection.start();
            //3.创建Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //4.创建目标
            Destination destination = session.createQueue("myMQ");
            //5.创建生成者
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            //6.发送消息
            for (int i = 0; i < 10; i++) {
                logger.debug("producer msssage : " + i);
                TextMessage msg = session.createTextMessage("producer msssage : " + i);
                Thread.sleep(1000);
                producer.send(msg);
            }
            //session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
