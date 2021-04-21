package com.obtk.auth.pub.controller;

import com.obtk.auth.pub.domain.Role;
import com.obtk.auth.pub.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(roleService.findAll());
    }
    @GetMapping("/menus/{roleId}")
    public ResponseEntity<?> findRoleMenuIds(@PathVariable int roleId){
        //查询该角色编号的所有菜单编号
        return ResponseEntity.ok(roleService.findRoleMenuIds(roleId));
    }

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody Role role){
        return ResponseEntity.ok(roleService.addRole(role));
    }
    @PostMapping("/rolemenu")
    public ResponseEntity<?> editRoleMenu(@RequestParam int roleId,
                                          @RequestParam String ids){
        //ids.split将字符串按指定的字符切割成字符串数组
        //Arrays.asList将数组快速转换成集合
        List<Integer> menuIds=new ArrayList<>();
        if(ids.length()>0){//判断菜单编号字符串不为空字符串
            List<String> list= Arrays.asList(ids.split("-"));
            //方法引用Integer::parseInt相当于s->Integer.parseInt(s)
            menuIds=list.stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        roleService.editRoleMenu(roleId,menuIds);
        return ResponseEntity.ok(1);
    }
}
