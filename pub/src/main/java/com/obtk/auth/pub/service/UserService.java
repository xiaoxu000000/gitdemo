package com.obtk.auth.pub.service;

import com.obtk.auth.pub.domain.User;
import com.obtk.auth.pub.mapper.RoleMapper;
import com.obtk.auth.pub.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    public User login(String username,String pwd){
        User user=userMapper.findByParam(username, pwd);
        if(!Objects.isNull(user)){
            //用户不为空，则查询用户角色集合
            user.setRoles(roleMapper.findRoles(user.getUserId()));
        }
        return user;
    }

    public List<User> findAll(){
        return userMapper.findAll();
    }

    public List<Integer> findRoleIds(int userId){
        return roleMapper.findRoles(userId).stream()
                .map(role -> role.getRoleId()).collect(Collectors.toList());
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void editUserRole(int userId,int roleId,int authid){
        if(authid==0){
            //撤销角色
            userMapper.deleteUserRole(userId,roleId);
        }else{
            //授权角色
            userMapper.addUserRole(userId,roleId);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateState(int userId,String state){
        userMapper.updateState(userId, state);
    }
}
