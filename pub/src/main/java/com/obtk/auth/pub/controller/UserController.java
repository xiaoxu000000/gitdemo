package com.obtk.auth.pub.controller;

import com.obtk.auth.pub.domain.User;
import com.obtk.auth.pub.domain.UserMenu;
import com.obtk.auth.pub.service.MenuService;
import com.obtk.auth.pub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String pwd,
                                   HttpSession session){
        User user=userService.login(username, pwd);
        Map<String,Object> map=new HashMap<>();
        if(Objects.isNull(user)){
            map.put("msg","用户名或密码错误！");
        }else{
            if(user.getState().equals("禁用")){
                map.put("msg","账号被禁用，请与管理员联系!");
            }else if(user.getRoles().size()==0){
                map.put("msg","账号未授权，请与管理员联系!");
            }else{
                map.put("suc",true);
                session.setAttribute("loginer",user);
            }
        }
        return ResponseEntity.ok(map);
    }
    @GetMapping("/sessionUser")
    public ResponseEntity<?> findLoginUser(HttpSession session){
        User loginer=(User)session.getAttribute("loginer");
        //定义向前端返回的对象
        Map<String,Object> map=new HashMap<>();
        if(!Objects.isNull(loginer)){
            map.put("user",loginer);//如果已经登录，返回登录用户信息
            //查询用户菜单集合
            List<UserMenu> menus=menuService.findUserMenu(loginer.getRoles());
            map.put("menus",menus);
        }
        return ResponseEntity.ok(map);
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/roleIds")
    public ResponseEntity<?> findRoleIds(@RequestParam("userId") int userId){
        return ResponseEntity.ok(userService.findRoleIds(userId));
    }
    @PostMapping("/roleedit")
    public ResponseEntity<?> editrole(@RequestParam("userId") int userId,
                                      @RequestParam("roleId")int roleId,
                                      @RequestParam("authid")int authid){
        userService.editUserRole(userId, roleId, authid);
        return ResponseEntity.ok(1);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateState(@PathVariable int userId,
                                         @RequestBody User user){
        userService.updateState(userId,user.getState());
        return ResponseEntity.ok(1);
    }
}
