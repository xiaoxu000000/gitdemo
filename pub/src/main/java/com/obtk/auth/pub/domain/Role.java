package com.obtk.auth.pub.domain;

import java.io.Serializable;

/**
 * 角色实体类
 */
public class Role implements Serializable {
    private Integer roleId;
    private String roleName;
    private String beizhu="";

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
