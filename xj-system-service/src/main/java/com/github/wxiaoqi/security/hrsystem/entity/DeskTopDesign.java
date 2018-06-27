package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


//常用查询
@Table(name = "DeskTopDesign")
@Data
public class DeskTopDesign {

    //人员id
    @Column(name = "UserId")
    private String userid;

    @Column(name = "Detail")
    private String detail;

    @Column(name = "Kind")
    private String kind;
}