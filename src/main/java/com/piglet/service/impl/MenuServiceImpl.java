package com.piglet.service.impl;

import com.alibaba.fastjson.JSON;
import com.piglet.domain.Menu;
import com.piglet.domain.RoleMenu;
import com.piglet.domain.Tree;
import com.piglet.domain.UserRole;
import com.piglet.mapper.MenuMapper;
import com.piglet.service.MenuService;
import com.piglet.util.BuildTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public Set<String> listPerms(Integer userId) {
        List<String> perms = menuMapper.listPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Tree<Menu>> leftMenuTree(Integer userId,Integer topMeunId) {
        List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        List<Menu> menus = menuMapper.listMenuByUserId(params);
        for (Menu menu : menus) {
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setTitle(menu.getMenuName());
            tree.setSpread(true);
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", menu.getUrl());
            attributes.put("icon", menu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 首页左侧菜单树默认顶级菜单为1，根据数据库实际情况调整
        List<Tree<Menu>> list = BuildTree.buildList(trees,String.valueOf(topMeunId));
        return list;
    }

    @Override
    public List<Menu> getSubs(Integer userId) {
        Map<String,Object> params = new HashMap<>();
        params.put("type","-1");
        return menuMapper.listMenuByUserId(params);
    }

    @Override
    public List<Tree<Menu>> menuTree(Integer homepage) {
        List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
        List<Menu> menus = menuMapper.menuTree();
        for(Menu menu : menus){
            System.out.print("z");
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setTitle(menu.getMenuName());
            tree.setSpread(true);
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", menu.getUrl());
            attributes.put("icon", menu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        List<Tree<Menu>> list = BuildTree.buildList(trees,homepage+"");
        return list;
    }

    @Override
    public List<Integer> roleMenuIds(Map<String, Object> params) {
        return menuMapper.roleMenuIds(params);
    }

    @Override
    public void saveRoleMenu(Map<String, Object> params) {
        Integer roleId = Integer.parseInt((String)params.get("roleId"));
        menuMapper.removeByRoleId(roleId);
        List<Integer> menuList = JSON.parseArray((String)params.get("menus"),Integer.TYPE);
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        if(menuList.size()>0){
            for(Integer menuId : menuList){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                list.add(roleMenu);
            }
            menuMapper.saveRoleMenu(list);
        }
    }

    @Override
    public List<Menu> menuList() {
        return menuMapper.menuTree();
    }

    @Override
    public int edit(Map<String, Object> params) {
        return menuMapper.edit(params);
    }

    @Override
    public int selectCount(Map<String, Object> params) {
        return menuMapper.selectCount(params);
    }

    @Override
    public List<Menu> findMenu(int type) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",'0');
        return menuMapper.findMenu(params);
    }

    @Override
    public int save(Menu menu) {
        return menuMapper.insert(menu);
    }
}
