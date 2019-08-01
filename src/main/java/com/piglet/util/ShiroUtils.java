package com.piglet.util;

import com.piglet.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

public class ShiroUtils {
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
    public static boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }
    public static User getUser() {
        User user = (User) getSubjct().getPrincipal();
        return user;
    }
    public static Integer getUserId() {
        return getUser().getUserId();
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static String getSessionId(HttpServletRequest request) {
        return  request.getSession().getId();
    }

}
