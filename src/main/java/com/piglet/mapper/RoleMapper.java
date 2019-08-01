package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.Role;
import com.piglet.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    int roleCount(Map<String, Object> params);

    List<Map<String, Object>> roleList(Map<String, Object> params);

    List<Integer> userRole(String userId);

    void saveUserRole(List<UserRole> list);

    void removeByUserId(Integer userId);

    int edit(Map<String, Object> params);
}
