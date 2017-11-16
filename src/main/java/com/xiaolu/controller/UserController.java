package com.xiaolu.controller;

import com.xiaolu.domain.User;
import com.xiaolu.service.UserService;
import com.xiaolu.util.ParamsReqAndResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chinaD on 2017/9/22.
 */
@Controller
@RequestMapping("/go")
public class UserController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @Autowired
    UserService userService;

    /*
     * 方式一
     * 通过类的映射路径+请求方式
    */
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        userService.selectByPrimaryKey("1");
        return "user";
    }

    /*
     *方式二
     * 通过类的映射+方法的映射+请求方式
    */
    @RequestMapping(path = "/user",method = RequestMethod.GET)
    public String getUser() {
        User user = userService.selectByPrimaryKey("1");
        return "user";
    }

    /**
     * 方式三
     * 通过URL模板+请求方式
    * */
    @RequestMapping(path = "/user/{ID}",method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable String ID, Model model) {
        model.addAttribute(userService.selectByPrimaryKey(ID));
        System.out.println("3");
        Map userMap = new HashMap();
        userMap.put(ID,userService.selectByPrimaryKey(ID));
        return new ModelAndView("user",userMap);
    }

    @RequestMapping(path = "/user/{ID}/info", method=RequestMethod.GET)
    public void userData(@PathVariable String ID,HttpServletRequest request, HttpServletResponse response)
    {
        User user = userService.selectByPrimaryKey(ID);
        Map<String,Object> userMap = new HashMap<String, Object>();
        userMap.put(ID,user);
        String jsonResult = this.paramsReqAndResp.getJSONString(userMap);
        this.paramsReqAndResp.renderData(response, jsonResult);

    }

}
