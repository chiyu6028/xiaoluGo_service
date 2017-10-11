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
 * Created by chinaD on 2017/9/27.
 * 登录和注册
 */
@Controller
public class LoginController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @Autowired
    UserService userService;

    @RequestMapping(path = "/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(path = "/main")
    public String main(){
        return "main";
    }

    @RequestMapping(path = "/loginSubmit", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        Map result = new HashMap();
        result.put("msg","登录成功");
        String user_id = request.getParameter("user_id");
        String user_password = request.getParameter("user_password");
        String validCode = request.getParameter("validCode");

        String sessionId = (String) session.getAttribute("sessionCode");


        //用户名
        if ("".equals(user_id) || user_id == null){
            result.put("msg","用户名不能为空");
            result.put("flg",2);
            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
        }

        //密码
        if ("".equals(user_password) || user_password == null){
            result.put("msg","密码不能为空");
            result.put("flg",3);
            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
        }

        //验证码校对
        if (!validCode.toLowerCase().equals(sessionId.toLowerCase())){
            result.put("msg","验证码不正确");
            result.put("flg",4);
            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
        }

        //用户密码判断
        User user = userService.selectUserBypassword(user_id, MD5.addMD5(user_password));
        if (user!=null){
            result.put("msg","登录成功");
            result.put("flg",1);
            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);

            //session添加登录标识。
            session.setAttribute("login","successLogin");
        }

    }
}
