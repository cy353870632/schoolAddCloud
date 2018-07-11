package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "system_role")
@Data
public class Role extends BaseEntity{


    private String r_name;//角色名称

    private String sort;//排序，依照权限大小分配，权限越大，排序越大

    private String r_name_china;//角色名称中文


}