package com.piglet.controller;

import com.piglet.domain.User;
import com.piglet.service.MenuService;
import com.piglet.service.UserService;
import com.piglet.util.Result;
import com.piglet.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.shiro.session.Session;
import static com.piglet.util.ShiroUtils.getUser;
import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class LoginController {

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    @RequestMapping({"","login"})
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        User user = ShiroUtils.getUser();
        int icount = userService.icount(user.getUserId());
        Session session = ShiroUtils.getSession();
        session.setAttribute("icount", icount);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model){
        User user=getUser();
        model.addAttribute("user",user);
        return "welcome";
    }

    @PostMapping("/checklogin")
    @ResponseBody
    Result checklogin(String username, String password, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.error(e.getMessage());
        }
    }
}

