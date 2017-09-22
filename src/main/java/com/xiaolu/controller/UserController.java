package com.xiaolu.controller;

import com.xiaolu.domain.User;
import com.xiaolu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by chinaD on 2017/9/22.
 */
@Controller
@RequestMapping("/go")
public class UserController {

    @Autowired
    UserService userService;

    /*
     * 方式一
     * 通过类的映射路径+请求方式
    */
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        userService.selectByPrimaryKey("1");
        System.out.println("1");
        return "user";
    }

    /*
     *方式二
     * 通过类的映射+方法的映射+请求方式
    */
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String getUser() {
        userService.selectByPrimaryKey("1");
        System.out.println("2");
        return "user";
    }

    /**
     * 方式三
     * 通过URL模板+请求方式
    * */
    @RequestMapping(path = "/user/{ID}",method = RequestMethod.GET)
    public String getUserById(@PathVariable String ID, Model model) {
        model.addAttribute(userService.selectByPrimaryKey(ID));
        System.out.println("3");
        return "user";
    }
}
