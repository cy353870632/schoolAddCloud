package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 菜单明细表
 *
 * */
@Table(name = "WebModuleInfo")
@Data
public class WebModuleInfo {

    //模块id
    @Id
    @Column(name = "ModuleID")
    private String moduleid;

    @Column(name = "ModuleName")
    private String modulename;

    private String fa;

    @Column(name = "URL")
    private String url;
}