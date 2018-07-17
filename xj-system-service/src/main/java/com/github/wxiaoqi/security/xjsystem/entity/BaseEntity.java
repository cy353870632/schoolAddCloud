package com.github.wxiaoqi.security.xjsystem.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;


@Data
public class BaseEntity {

    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;
}