package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


//常用查询
@Table(name = "WebReports")
@Data
public class WebReports {


    @Column(name = "Rpt_ID")
    private String rpt_id;

    @Column(name = "Rpt_Name")
    private String rpt_name;

}