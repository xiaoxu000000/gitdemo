package com.obtk.auth.pub.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树节点对应实体类
 */
public class MenuNode implements Serializable {

    private Integer id;
    private String text;
    private List<MenuNode> children;//子节点集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }
}
