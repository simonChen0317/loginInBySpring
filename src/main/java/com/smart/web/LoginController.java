package com.smart.web;

import com.smart.domain.LoginCommand;
import com.smart.domain.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//标注成为一个Spring mvc的controller
@Controller
public class LoginController {
    private UserService userService;

    //负责处理/index.html的请求
    @RequestMapping(value = "/login.html")
    public  String loginPage(){
        return "login";
    }

    //负责处理/loginCheck.html的请求
    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request,LoginCommand loginCommand){
        boolean isValidUser = userService.hasMatchUser(loginCommand.getUserName(),loginCommand.getPassword());
        if(!isValidUser){
            //ModelAndView第一个参数是视图逻辑名，第二、第三个参数分别表示数据模型名称和数据模型对象，数据模型对象将以数据模型名称为参数放置到request的属性中
            return new ModelAndView("login","error","用户名或密码错误。");
        }else {
            User user = userService.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user",user);
            return new ModelAndView("main");
        }
    }
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}

