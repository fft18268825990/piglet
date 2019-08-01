package com.piglet.service;

import com.piglet.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    int roleCount(Map<String, Object> params);

    List<Map<String, Object>> roleList(Map<String, Object> params);

    List<Integer> userRole(String userId);

    void saveUserRole(Map<String, Object> params);

    int save(Role role);

    int edit(Map<String, Object> params);
}
