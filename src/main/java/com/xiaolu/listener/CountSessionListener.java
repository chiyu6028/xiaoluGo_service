package com.xiaolu.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 使用spring-session 后 监听直接使用失效。需要spring配置下才可使用
 * Created by chinaD on 2017/11/24.
 */
public class CountSessionListener implements HttpSessionListener {

    private static int count = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        count++;
        session.setAttribute("count",count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        count --;
        session.setAttribute("count",count);
    }
}
