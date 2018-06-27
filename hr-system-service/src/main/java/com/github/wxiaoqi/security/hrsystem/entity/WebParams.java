package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 菜单明细表
 *
 * */
@Table(name = "WebParams")
@Data
public class WebParams {

    @Column(name = "ParamName")
    private String param_name;

    @Column(name = "ParamValue")
    private String param_value;
}