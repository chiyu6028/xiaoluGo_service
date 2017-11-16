package com.xiaolu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chinaD on 2017/11/16.
 */
@Controller
public class ChatController {

    @RequestMapping(path = "/chat/{user_id}", method = RequestMethod.GET)
    public String toChat(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response){
        String message = request.getParameter("message");
        return "chat";
    }

}
