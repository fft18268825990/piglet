package com.piglet.service;

import com.piglet.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    List<User> userList(Map<String, Object> params);

    User get(Integer userId);

    Integer userCount(Map<String, Object> params);

    int save(User user);

    int edit(Map<String, Object> params);

    List<Map<String,Object>> countlist(Map<String, Object> params);

    int icount(Integer userId);

    User findUserById (Integer userId);
}
