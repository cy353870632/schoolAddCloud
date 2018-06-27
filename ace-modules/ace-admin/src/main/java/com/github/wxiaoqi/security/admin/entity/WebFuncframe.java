package com.github.wxiaoqi.security.admin.entity;

import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "WebFuncFrame")
@Data
public class WebFuncframe {
    @Id
    private Integer id;

    private String code;

    private String title;

    @Column(name = "parent_id")
    private Integer parentId = AdminCommonConstant.ROOT;

    private String href;

    private String icon;

    private String type;

    private String description;

    @Column(name = "crt_time")
    private Date crtTime;

    @Column(name = "crt_user")
    private String crtUser;

    @Column(name = "crt_name")
    private String crtName;

    @Column(name = "crt_host")
    private String crtHost;

    @Column(name = "upd_time")
    private Date updTime;

    @Column(name = "upd_user")
    private String updUser;

    @Column(name = "upd_name")
    private String updName;

}