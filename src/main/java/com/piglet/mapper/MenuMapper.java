package com.piglet.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.piglet.domain.Menu;
import com.piglet.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> listPerms(Integer userId);

    List<Menu> listMenuByUserId(Map<String, Object> params);

    List<Menu> menuTree();

    List<Integer> roleMenuIds(Map<String, Object> params);

    void removeByRoleId(Integer roleId);

    void saveRoleMenu(List<RoleMenu> list);

    int edit(Map<String, Object> params);

    int selectCount(Map<String, Object> params);

    List<Menu> findMenu(Map<String, Object> params);
}
