package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
public class BaseEntity {
    @Id
    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
}