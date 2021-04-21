package com.obtk.auth.pub.mapper;

import com.obtk.auth.pub.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from user where pwd=MD5(#{pwd}) and username=#{username}")
    User findByParam(@Param("username") String username,
                     @Param("pwd") String pwd);
    @Select("select userid,username,xingming,phone,state from user")
    List<User> findAll();
    //添加用户角色关联
    @Insert("insert into user_role(userid,roleid)values(#{userId},#{roleId})")
    int addUserRole(@Param("userId") int userId,
                    @Param("roleId") int roleId);
    //删除用户角色关联
    @Delete("delete from user_role where userid=#{userId} and roleid=#{roleId}")
    int deleteUserRole(@Param("userId") int userId,
                       @Param("roleId") int roleId);
    //修改用户账号状态
    @Update("update user set state=#{state} where userid=#{userId}")
    int updateState(@Param("userId")int userId,
                    @Param("state") String state);

}
