package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "WebMessageMap")
@Data
public class WebMessageMap {
    @Id
    @Column(name = "MapID")
    private Integer mapid;

    @Column(name = "MessageID")
    private String messageid;

    private Integer a0188 ;

    @Column(name = "ReadTag")
    private Integer readtag;

    private Integer important;
}