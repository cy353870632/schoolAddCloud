package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "A01")
@Data
public class User {
    @Id
    private Integer A0188;
    //工号，账号
    private String A0190;

    private String password;

    @Column(name = "A0101")
    private String name;

    private String email;
}