package com.piglet.realm;

import com.piglet.config.ApplicationContextRegister;
import com.piglet.domain.User;
import com.piglet.service.MenuService;
import com.piglet.service.UserService;
import com.piglet.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Integer userId = ShiroUtils.getUserId();
        MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
        Set<String> perms = menuService.listPerms(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", username);
        map.put("state", 1);
        String password = new String((char[]) token.getCredentials());

        UserService userService = ApplicationContextRegister.getBean(UserService.class);
        // 查询用户信息
        List<User> users = userService.userList(map);

        User user = null;
        if(users.size()>0){
            user = users.get(0);
        }
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }


        Session session = ShiroUtils.getSession();
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userName", user.getUsername());


        // 密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

//		session.setTimeout(Constant.sessionTimeout);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}