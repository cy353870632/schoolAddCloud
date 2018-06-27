package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 菜单明细表
 *
 * */
@Table(name = "WebFuncFrame")
@Data
public class WebFuncFrame {
    @Id
    @Column(name = "Func_Code")
    private String funccode;

    //模块id
    @Column(name = "ModuleID")
    private String moduleid;

    @Column(name = "Func_Name")
    private String funcname;

    @Column(name = "Func_Url")
    private String funcurl;

    @Column(name = "Func_Parent")
    private String funcparent;

    private String fa;

    @Column(name = "FunTag")
    private Integer funtag;
}