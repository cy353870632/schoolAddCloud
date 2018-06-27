package com.github.wxiaoqi.security.hrsystem.vo;

import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


//常用查询
@Table(name = "ryQuerySuites")
@Data
public class RyQuerySuitesVo extends RyQuerySuites{


    @Column(name = "ModuleName")
    private String module_name;

    @Column(name = "ModuleId")
    private String module_id;
}