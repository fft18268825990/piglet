package com.piglet.service.impl;

import com.alibaba.fastjson.JSON;
import com.piglet.domain.Role;
import com.piglet.domain.UserRole;
import com.piglet.mapper.RoleMapper;
import com.piglet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public int roleCount(Map<String, Object> params) {
        return roleMapper.roleCount(params);
    }

    @Override
    public List<Map<String, Object>> roleList(Map<String, Object> params) {
        if((String)params.get("page")!=null && (String)params.get("page")!=""
                &&(String)params.get("limit")!=null && (String)params.get("limit")!="") {
            int page = Integer.parseInt((String) params.get("page"));
            int limit = Integer.parseInt((String) params.get("limit"));
            params.put("offset", (page - 1 )* limit);
        }
        return roleMapper.roleList(params);
    }

    @Override
    public List<Integer> userRole(String userId) {
        return roleMapper.userRole(userId);
    }

    @Override
    public void saveUserRole(Map<String, Object> params) {
        Integer userId = Integer.parseInt((String)params.get("userId"));
        roleMapper.removeByUserId(userId);
        List<Integer> roleList = JSON.parseArray((String)params.get("roles"),Integer.TYPE);
        List<UserRole> list = new ArrayList<UserRole>();
        if(roleList.size()>0) {
            for (Integer roleId : roleList) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                list.add(userRole);
            }
            roleMapper.saveUserRole(list);
        }
    }

    @Override
    public int save(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int edit(Map<String, Object> params) {
        return roleMapper.edit(params);
    }
}
