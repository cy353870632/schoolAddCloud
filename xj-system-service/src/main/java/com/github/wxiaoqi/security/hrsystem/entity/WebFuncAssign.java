package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 菜单和权限表
 *
 * */
@Table(name = "WebFuncAssign")
@Data
public class WebFuncAssign {
    @Id
    @Column(name = "FunAssignID")
    private Integer fun_assign_id;

    //模块id
    @Column(name = "ActorId")
    private Integer actorid;

    @Column(name = "Func_Code")
    private Integer funccode;

    @Column(name = "Inherited")
    private Integer inherited;

}