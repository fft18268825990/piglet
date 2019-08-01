package com.piglet.service.impl;

import com.piglet.mapper.UserMapper;
import com.piglet.domain.User;
import com.piglet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> userList(Map<String, Object> params) {
        if((String)params.get("page")!=null && (String)params.get("page")!=""
                &&(String)params.get("limit")!=null && (String)params.get("limit")!="") {
            int page = Integer.parseInt((String) params.get("page"));
            int limit = Integer.parseInt((String) params.get("limit"));
            params.put("offset", (page - 1 )* limit);
        }
        return userMapper.userList(params);
    }

    @Override
    public User get(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public Integer userCount(Map<String, Object> params) {
        return userMapper.userCount(params);
    }

    @Override
    public int save(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int edit(Map<String, Object> params) {
        return userMapper.edit(params);
    }

    @Override
    public List<Map<String, Object>> countlist(Map<String, Object> params) {
        return userMapper.countlist(params);
    }

    @Override
    public int icount(Integer userId) {
        return userMapper.icount(userId);
    }

    @Override
    public User findUserById(Integer userId) {
        return userMapper.selectById(userId);
    }
}
