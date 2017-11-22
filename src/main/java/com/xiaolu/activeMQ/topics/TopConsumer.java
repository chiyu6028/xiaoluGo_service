package com.xiaolu.activeMQ.topics;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by chinaD on 2017/11/22.
 */
public class TopConsumer {



    public static void main(String[] args) throws InterruptedException {

        Logger logger = LoggerFactory.getLogger(TopConsumer.class);

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
            //5.创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            //6.接收消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage msg = (TextMessage) message;
                    try {
                        logger.debug("consumer:"+msg.getText());
                        //session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread.sleep(100000);

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
