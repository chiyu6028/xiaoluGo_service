package com.xiaolu.activeMQ.spring;

import org.springframework.stereotype.Component;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

/**
 * 异常的监听
 * Created by chinaD on 2017/11/23.
 */
@Component
public class JmsExceptionListener implements ExceptionListener {
    @Override
    public void onException(JMSException e) {
        e.printStackTrace();
    }
}
