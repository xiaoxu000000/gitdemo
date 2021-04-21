package com.obtk.auth.pub.service;

import com.obtk.auth.pub.domain.Menu;
import com.obtk.auth.pub.domain.MenuNode;
import com.obtk.auth.pub.domain.Role;
import com.obtk.auth.pub.domain.UserMenu;
import com.obtk.auth.pub.mapper.MenuMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;
    //根据用户的角色信息查询用户菜单块集合
    public List<UserMenu> findUserMenu(List<Role> roles){
        //集合的流处理操作
        //roles.stream:将List接口集合转换成内存中的流
        //map方法对流中的每个元素进行处理，支持Lamda表达式
        //collect收集处理过的流数据转换成List接口的集合
        List<Integer> roleids=roles.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
        //等同于
//        List<Integer> list=new ArrayList<>();
//        for(Role role:roles){
//            list.add(role.getRoleId());
//        }
        List<UserMenu> list=new ArrayList<>();
        //查询对应角色编号的一级菜单集合
        List<Menu> menus=menuMapper.findTopMenu(roleids);
        for(Menu menu:menus){
            UserMenu userMenu=new UserMenu();
            userMenu.setTopMenu(menu);//设置一级菜单属性
            //查询对应角色二级菜单集合
            List<Menu> menus1=menuMapper.findChildMenu(roleids,menu.getMenuId());
            userMenu.setChildMenus(menus1);//设置二级菜单集合属性
            list.add(userMenu);//添加到要返回的用户菜单集合
        }
        return list;
    }

    public List<Menu> findAll(){
        return menuMapper.findAll();
    }

    public List<MenuNode> findMenuTree(){
        List<MenuNode> list=new ArrayList<>();
        //查询所有一级菜单
        List<Menu> menus=menuMapper.findAllTopMenu();
        //遍历所有一级菜单，将其转换为菜单节点
        for(Menu menu:menus){
            MenuNode menuNode=new MenuNode();
            menuNode.setId(menu.getMenuId());
            menuNode.setText(menu.getTitle());
            //查询一级菜单下包含的所有二级菜单
            List<Menu> childmenus=menuMapper.findAllChildMenu(menu.getMenuId());
            List<MenuNode> childnodes=new ArrayList<>();
            //遍历二级菜单集合，将二级菜单转换成二级树节点
            for(Menu menu1:childmenus){
                MenuNode cnode=new MenuNode();
                cnode.setId(menu1.getMenuId());
                cnode.setText(menu1.getTitle());
                childnodes.add(cnode);
            }
            menuNode.setChildren(childnodes);
            list.add(menuNode);
        }
        return  list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addMenu(Menu menu){
        return menuMapper.addMenu(menu);
    }

    public Menu findById(int menuId){
        return menuMapper.findById(menuId);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateMenu(Menu menu){
        return menuMapper.updateMenu(menu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delMenu(int menuId){
        //查询菜单是否被角色所引用
        int count=menuMapper.findRoleMenu(menuId);
        if(count==0){
            //如果没有，则删除该菜单
            return menuMapper.delMenu(menuId);
        }else{
            return -1;
        }
    }

}
