package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.Table;

//角色和菜单关联类
@Table(name = "system_menu")
@Data
public class Menu extends BaseEntity{

    private String title;//菜单名称
    private String parent_id;//父id
    private String end_mark;//是否为终结点
    private String state;//状态，1为可用，0位不可用
    private String code_path;//跳转的路由路径
}