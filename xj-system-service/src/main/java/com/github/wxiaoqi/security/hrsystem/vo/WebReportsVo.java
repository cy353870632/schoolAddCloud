package com.github.wxiaoqi.security.hrsystem.vo;

import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.entity.WebReports;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


//常用查询
@Table(name = "ryQuerySuites")
@Data
public class WebReportsVo extends WebReports{

    @Column(name = "ModuleName")
    private String module_name;

}