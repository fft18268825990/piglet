package com.piglet.controller;

import com.piglet.domain.User;
import com.piglet.service.UserService;
import com.piglet.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    @Autowired
    private UserService userService;
    public User getUser() {
        return userService.get(ShiroUtils.getUser().getUserId());
    }

    public Integer getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }
}
