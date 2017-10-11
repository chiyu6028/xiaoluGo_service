package com.xiaolu.controller;

import com.xiaolu.domain.User;
import com.xiaolu.service.UserService;
import com.xiaolu.util.MD5;
import com.xiaolu.util.ParamsReqAndResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chinaD on 2017/10/11.
 */

@Controller
public class RegisterController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @Autowired
    UserService userService;

    @RequestMapping(path = "/register")
    public String register(){
        return "register";
    }

    @RequestMapping(path = "/registerSubmit", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        String user_id = request.getParameter("user_id");
        String user_password = MD5.addMD5(request.getParameter("user_password"));
        String user_name = request.getParameter("user_name");

        User user = new User();
        user.setUser_id(Integer.valueOf(user_id));
        user.setUser_name(user_name);
        user.setUser_password(user_password);
        int flag = userService.insertUser(user);

        Map resultMap = new HashMap();
        resultMap.put("flag",flag);
        String result = paramsReqAndResp.getJSONString(resultMap);

        paramsReqAndResp.renderData(response,result);
    }

}
