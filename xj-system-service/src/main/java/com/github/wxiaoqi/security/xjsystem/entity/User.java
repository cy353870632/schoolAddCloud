package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "system_user")
@Data
public class User extends BaseEntity{


    private String u_name;//昵称

    private String pwd;

    private String l_name;//登录名

    private String role;//code,role关联

    private String image;//头像

}