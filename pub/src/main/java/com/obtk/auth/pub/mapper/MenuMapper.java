package com.obtk.auth.pub.mapper;

import com.obtk.auth.pub.domain.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MenuMapper {
    //按角色编号集合查询所有一级菜单集合
    List<Menu> findTopMenu(List<Integer> roleids);
    //按角色编号集合和父菜单编号查询所有二级菜单集合
    List<Menu> findChildMenu(@Param("roleids") List<Integer> roleids,
                             @Param("parentId") int parentId);
    @Select("select menuid,title,ifnull(url,'') url,seq from menu order by seq")
    List<Menu> findAll();
    @Select("select menuid,title,ifnull(url,'') url,seq,ifnull(parentid,0) parentid," +
            "ifnull(beizhu,'') beizhu from menu where menuid=#{menuId}")
    Menu findById(@Param("menuId")int menuId);
    //查询所有一级菜单
    @Select("select * from menu where parentid is null")
    List<Menu> findAllTopMenu();
    //查询指定一级菜单下的所有二级菜单
    @Select("select * from menu where parentid=#{parentId}")
    List<Menu> findAllChildMenu(@Param("parentId")int parentid);

    @Insert("insert into menu values(null,#{title},#{url},#{seq},#{parentId},#{beizhu})")
    int addMenu(Menu menu);
    @Update("update menu set title=#{title},url=#{url},seq=#{seq},parentid=#{parentId}," +
            "beizhu=#{beizhu} where menuid=#{menuId}")
    int updateMenu(Menu menu);
    @Delete("delete from menu where menuid=#{menuId}")
    int delMenu(@Param("menuId") int menuId);
    @Select("select count(menuid) from role_menu where menuid=#{menuId}")
    int findRoleMenu(@Param("menuId") int menuId);





}
