package com.github.wxiaoqi.security.admin.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "A01")
@Data
public class User {
    @Id
    @Column(name = "A0188")
    private Integer id;//主键id

    @Column(name = "A0190")
    private String username;

    @Column(name = "PasswordString")
    private String password;

    @Column(name = "A0101")
    private String name;

    @Column(name = "CellPhone")
    private String mobilePhone;

    @Column(name = "email")
    private String email;

}