package com.piglet.controller;

import com.piglet.domain.User;
import com.piglet.service.UserService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/userManage")
    public String userManage(){
        return "admin-list";
    }

    @RequestMapping("/adminAdd")
    public String adminAdd(){
        return "admin-add";
    }

    @RequestMapping("/adminEdit")
    public String adminEdit(){
        return "admin-edit";
    }

    @RequestMapping("/userRole")
    public String userRole(){
        return "admin-role";
    }

    @RequestMapping("/editPwd")
    public String editPwd(){
        return "admin-pwd";
    }

    @RequestMapping("/userProductCount")
    public String userProductCount(){
        return "user-product";
    }

    @GetMapping("/userList")
    @ResponseBody
    public PageUtil userList(@RequestParam Map<String, Object> params){
        return new PageUtil("0","返回成功",userService.userCount(params),userService.userList(params));
    }

    @PostMapping("/saveUser")
    @ResponseBody
    public Result saveUser(@RequestParam Map<String, Object> params){
        User user = new User();
        user.setUsername((String)params.get("username"));
        user.setPassword((String)params.get("password"));
        user.setPhone((String)params.get("phone"));
        user.setRealname((String)params.get("realname"));
        user.setState(1);
        user.setCreateTime(new Date());
        user.setCreatePerson(getUserId());
        user.setDelFlag(0);
        if (userService.save(user) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/editUser")
    @ResponseBody
    public Result editUser(@RequestParam Map<String, Object> params){
        String userId = (String)params.get("userId");
        if(userId==null || userId=="") {
            return Result.error();
        }
        if (userService.edit(params) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/countlist")
    @ResponseBody
    public PageUtil countlist(@RequestParam Map<String, Object> params){
        return new PageUtil("0","返回成功",0,userService.countlist(params));
    }


}
