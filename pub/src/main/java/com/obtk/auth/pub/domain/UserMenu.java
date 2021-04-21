package com.obtk.auth.pub.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 一个菜单模块实体类
 */
public class UserMenu implements Serializable {
    private Menu topMenu;//一级菜单
    private List<Menu> childMenus;//二级菜单集合

    public Menu getTopMenu() {
        return topMenu;
    }

    public void setTopMenu(Menu topMenu) {
        this.topMenu = topMenu;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }
}
