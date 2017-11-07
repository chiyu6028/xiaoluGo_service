package com.xiaolu.controller;

import com.xiaolu.domain.User;
import com.xiaolu.service.LoginLogService;
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

    @Autowired
    LoginLogService loginLogService;

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
        String msg = "登录成功";
        result.put("msg",msg);
        String user_id = request.getParameter("user_id");
        String user_password = request.getParameter("user_password");
        String validCode = request.getParameter("validCode");

        String sessionId = (String) session.getAttribute("sessionCode");

        //用户名
        if ("".equals(user_id) || user_id == null){
            msg = "用户名不能为空";
            result.put("msg",msg);
            result.put("flg",2);
            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
            return;
        }

        //密码
        if ("".equals(user_password) || user_password == null){
            msg = "密码不能为空";
            result.put("msg",msg);
            result.put("flg",3);

            //记录日志
            loginLog(request,user_id ,msg);

            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
            return;
        }

        //验证码校对
        if (!validCode.toLowerCase().equals(sessionId.toLowerCase())){
            msg = "验证码不正确";
            result.put("msg",msg);
            result.put("flg",4);

            //记录日志
            loginLog(request,user_id ,msg);

            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
            return;
        }

        //用户密码判断
        User user = userService.selectUserBypassword(user_id, MD5.addMD5(user_password));
        if (user!=null){
            result.put("msg",msg);
            result.put("flg",1);

            //记录日志
            loginLog(request,user_id ,msg);

            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);

            //session添加登录标识。
            session.setAttribute("login","successLogin");
        }else{
            msg = "用户名或密码不正确";
            result.put("msg",msg);
            result.put("flg",5);

            //记录日志
            loginLog(request,user_id ,msg);

            String str = paramsReqAndResp.getJSONString(result);
            paramsReqAndResp.renderData(response,str);
            return;
        }
    }

    public int loginLog(HttpServletRequest request,String user_id, String msg){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        Map loginMap = new HashMap();
        loginMap.put("user_id",user_id);
        loginMap.put("login_ip",ip);
        loginMap.put("login_msg",msg);
        int i = loginLogService.insertLoginLog(loginMap);
        return i;
    }
}
