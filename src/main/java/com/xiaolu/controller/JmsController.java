package com.xiaolu.controller;

import com.xiaolu.activeMQ.spring.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/23.
 */
@Controller
public class JmsController {

    @Autowired
    QueueSender queueSender;

    @RequestMapping(path = "/jms" ,method = RequestMethod.GET)
    public String toJms(){
        return "jms";
    }

    @RequestMapping(path = "/jms/sendMap", method = RequestMethod.GET)
    public void sendMap(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        String content = request.getParameter("content");
        Map map = new HashMap();
        map.put("content",content);
        queueSender.sendMap(map);
    }

    @RequestMapping(path = "/jms/sendMessage", method = RequestMethod.GET)
    public void sendMessage(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        String content = request.getParameter("content");
        queueSender.send(content);
    }
}
