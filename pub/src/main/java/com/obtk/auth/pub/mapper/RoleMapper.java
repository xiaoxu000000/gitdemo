package com.obtk.auth.pub.mapper;

import com.obtk.auth.pub.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RoleMapper {
    @Select("select r.* from role r join user_role ur on " +
            "r.roleid=ur.roleid where ur.userid=#{userid}")
    List<Role> findRoles(@Param("userid") int userId);//按用户编号查该用户的角色集合
    @Select("select * from role")
    List<Role> findAll();

    //按角色编号查询所有关联菜单编号
    @Select("select menuid from role_menu where roleid=#{roleId}")
    List<Integer> findRoleMenuIds(@Param("roleId") int roleId);
    @Insert("insert into role values(null,#{roleName},#{beizhu})")
    int addRole(Role role);
    //按角色编号删除之前选择的菜单
    @Delete("delete from role_menu where roleid=#{roleId}")
    int deleteRoleMenu(@Param("roleId") int roleId);
    //添加单个角色和菜单关联项
    @Insert("insert into role_menu(roleid,menuid)values(#{roleId},#{menuId})")
    int addRoleMenu(@Param("roleId") int roleId,@Param("menuId") int menuId);
}
