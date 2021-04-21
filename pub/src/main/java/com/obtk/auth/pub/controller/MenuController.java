package com.obtk.auth.pub.controller;

import com.obtk.auth.pub.domain.Menu;
import com.obtk.auth.pub.domain.MenuNode;
import com.obtk.auth.pub.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(menuService.findAll());
    }
    @PostMapping("/tree/{state}")
    public ResponseEntity<?> findMenuTree(@PathVariable int state){
        List<MenuNode> list=menuService.findMenuTree();
        if(state>0){//判断是否需要添加0号节点
            MenuNode menuNode=new MenuNode();
            menuNode.setId(0);
            menuNode.setText("无父节点");
            list.add(menuNode);
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping
    public ResponseEntity<?> addMenu(@RequestBody Menu menu){
        int count=menuService.addMenu(menu);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/{menuId}")
    public ResponseEntity<?> findById(@PathVariable int menuId){
        return ResponseEntity.ok(menuService.findById(menuId));
    }
    @PutMapping
    public ResponseEntity<?> updateMenu(@RequestBody Menu menu){
        int count=menuService.updateMenu(menu);
        return ResponseEntity.ok(count);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> delMenu(@PathVariable int menuId){
        Map<String,Object> map=new HashMap<>();
        if(menuService.delMenu(menuId)>0){
            map.put("suc",true);
        }else{
            map.put("msg","该菜单有关联，不能删除！");
        }
        return ResponseEntity.ok(map);
    }


}
