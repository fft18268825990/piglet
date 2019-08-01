package com.piglet.controller;

import com.piglet.domain.Role;
import com.piglet.service.RoleService;
import com.piglet.util.PageUtil;
import com.piglet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.piglet.util.ShiroUtils.getUserId;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/roleManage")
    public String userManage(){
        return "role-list";
    }

    @RequestMapping("/roleAdd")
    public String roleAdd(){
        return "role-add";
    }

    @RequestMapping("/roleEdit")
    public String roleEdit(){
        return "role-edit";
    }

    @RequestMapping("/roleMenu")
    public String roleManage(){
        return "role-menu";
    }

    @GetMapping("/roleList")
    @ResponseBody
    public PageUtil roleList(@RequestParam Map<String, Object> params){
        return new PageUtil("0","返回成功",roleService.roleCount(params),roleService.roleList(params));
    }

    @PostMapping("/userRole")
    @ResponseBody
    public List<Integer> userRole(@RequestParam String userId){
        return roleService.userRole(userId);
    }

    @PostMapping("/saveUserRole")
    @ResponseBody
    public Result saveUserRole(@RequestParam Map<String, Object> params){
        roleService.saveUserRole(params);
        return Result.ok();
    }

    @PostMapping("/saveRole")
    @ResponseBody
    public Result saveRole(@RequestParam Map<String, Object> params){
        Role role = new Role();
        role.setRoleName((String)params.get("roleName"));
        role.setRoleDes((String)params.get("roleDes"));
        role.setCreateTime(new Date());
        role.setCreatePerson(getUserId());
        role.setDelFlag(0);
        if (roleService.save(role) > 0) {
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/editRole")
    @ResponseBody
    public Result editRole(@RequestParam Map<String, Object> params){
        String roleId = (String)params.get("roleId");
        if(roleId==null || roleId=="") {
            return Result.error();
        }
        if (roleService.edit(params) > 0) {
            return Result.ok();
        }
        return Result.error();
    }
}
