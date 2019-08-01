package com.piglet.controller;

import com.piglet.domain.Menu;
import com.piglet.domain.Tree;
import com.piglet.service.MenuService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("/menuManage")
    public String menuManage(){
        return "menu-list";
    }

    @RequestMapping("/menuAdd")
    public String menuAdd(Model model){
        model.addAttribute("Menus", menuService.findMenu(1));
        return "menu-add";
    }

    @RequestMapping("/menuEdit")
    public String menuEdit(){
        return "menu-edit";
    }

    @GetMapping("/menuList")
    @ResponseBody
    public PageUtil menuList(){
        return new PageUtil("0","返回成功",0,menuService.menuList());
    }

    @GetMapping("/menuTree")
    @ResponseBody
    List<Tree<Menu>> menuTree(@RequestParam Map<String, Object> params){
        List<Menu> subs = menuService.getSubs(getUserId());
        if(subs.size() > 0) {
            Integer homepage = subs.get(0).getMenuId();
            return menuService.menuTree(homepage);
        }else{
            return null;
        }
    }

    @GetMapping("/leftMenuTree")
    @ResponseBody
    List<Tree<Menu>> leftMenuTree(HttpServletRequest request){
        List<Menu> subs = menuService.getSubs(getUserId());
        if(subs.size() > 0) {
            Integer homepage = subs.get(0).getMenuId();
            return menuService.leftMenuTree(getUserId(),homepage);
        }else{
            return null;
        }
    }

    @GetMapping("/roleMenuIds")
    @ResponseBody
    List<Integer> roleMenuIds(@RequestParam Map<String, Object> params){
        return  menuService.roleMenuIds(params);
    }

    @PostMapping("/saveRoleMenu")
    @ResponseBody
    public Result saveRoleMenu(@RequestParam Map<String, Object> params){
        menuService.saveRoleMenu(params);
        return Result.ok();
    }

    @PostMapping("/editMenu")
    @ResponseBody
    public Result editMenu(@RequestParam Map<String, Object> params){
        String menuId = (String)params.get("menuId");
        if(menuId==null || menuId=="") {
            return Result.error();
        }
        if (menuService.edit(params) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/selectCount")
    @ResponseBody
    public int selectCount(@RequestParam Map<String, Object> params){
        return menuService.selectCount(params);
    }

    @PostMapping("/saveMenu")
    @ResponseBody
    public Result saveMenu(@RequestParam Map<String, Object> params){
        Menu menu = new Menu();
        String type = (String) params.get("type");
        if(type == "0" || "0".equals(type)){
            menu.setMenuName((String)params.get("menuName"));
            menu.setParentId(7);
            menu.setType(0);
            menu.setIcon((String)params.get("icon"));
            menu.setPerms((String)params.get("perms"));
            menu.setCreateTime(new Date());
            menu.setCreatePerson(getUserId());
            menu.setDelFlag(0);
        }else if(type == "1" || "1".equals(type)){
            menu.setMenuName((String)params.get("menuName"));
            menu.setParentId(Integer.parseInt((String)params.get("parentId")));
            menu.setType(1);
            menu.setUrl((String)params.get("url"));
            menu.setIcon((String)params.get("icon"));
            menu.setPerms((String)params.get("perms"));
            menu.setCreateTime(new Date());
            menu.setCreatePerson(getUserId());
            menu.setDelFlag(0);
        }
        if (menuService.save(menu) > 0) {
            return Result.ok();
        }
        return Result.error();
    }
}