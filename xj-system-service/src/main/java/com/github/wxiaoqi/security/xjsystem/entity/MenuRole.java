package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.Table;

//角色和菜单关联类
@Table(name = "system_menu_role")
@Data
public class MenuRole extends BaseEntity{


    private String menu_id;//角色名称
    private String role_id;//角色名称

}