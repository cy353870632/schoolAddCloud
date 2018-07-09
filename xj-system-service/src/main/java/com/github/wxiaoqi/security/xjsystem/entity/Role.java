package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "system_role")
@Data
public class Role extends BaseEntity{


    private String r_name;//角色名称


}