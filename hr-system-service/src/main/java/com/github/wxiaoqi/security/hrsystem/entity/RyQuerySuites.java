package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


//常用查询
@Table(name = "ryQuerySuites")
@Data
public class RyQuerySuites {


    @Column(name = "SuiteId")
    private String suite_id;

    @Column(name = "Caption")
    private String caption;

}