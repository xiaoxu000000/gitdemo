package com.obtk.auth.pub.service;

import com.obtk.auth.pub.domain.Role;
import com.obtk.auth.pub.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public List<Role> findAll(){
        return roleMapper.findAll();
    }

    public List<Integer> findRoleMenuIds(int roleId){
        return roleMapper.findRoleMenuIds(roleId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addRole(Role role){
        return roleMapper.addRole(role);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void editRoleMenu(int roleId,List<Integer> menudIds){
        //删除角色编号之前的菜单项
        roleMapper.deleteRoleMenu(roleId);
        if(menudIds.size()>0){
            //遍历菜单编号集合，逐个处理单个角色与菜单关联的添加
            for(Integer menuId:menudIds){
                roleMapper.addRoleMenu(roleId,menuId);
            }
        }
    }
}
