package com.piglet.service;

import com.piglet.domain.Menu;
import com.piglet.domain.Tree;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MenuService {

    Set<String> listPerms(Integer userId);

    List<Tree<Menu>> leftMenuTree(Integer userId,Integer topMeunId);

    List<Menu> getSubs(Integer userId);

    List<Tree<Menu>> menuTree(Integer homepage);

    List<Integer> roleMenuIds(Map<String, Object> params);

    void saveRoleMenu(Map<String, Object> params);

    List<Menu> menuList();

    int edit(Map<String, Object> params);

    int selectCount(Map<String, Object> params);

    List<Menu> findMenu(int type);

    int save(Menu menu);
}
