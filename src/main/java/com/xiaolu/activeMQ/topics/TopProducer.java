package com.xiaolu.activeMQ.topics;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by chinaD on 2017/11/22.
 */
public class TopProducer {



    public static void main(String[] args) throws InterruptedException {

        Logger logger = LoggerFactory.getLogger(TopProducer.class);

        //1.获取连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        //2.获取连接
        Connection connection = null;

        try {
            connection = factory.createConnection();
            connection.start();
            //3.获取Session
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //4.创建destination
            Destination destination = session.createTopic("myTopic");
            //5.创建生产者
            MessageProducer producer = session.createProducer(destination);
            //6.生产者发送消息
            for (int i = 0; i < 10; i++) {
                TextMessage msg = session.createTextMessage(" this is topic message" + i);
                logger.debug(" this is topic message" + i);
                producer.send(msg);
                Thread.sleep(1000);
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
