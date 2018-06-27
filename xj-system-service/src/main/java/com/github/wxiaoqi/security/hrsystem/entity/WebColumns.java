package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "WebColumns")
@Data
public class WebColumns {
    @Id
//    @Column(name = "ColumnID")
    private Integer ColumnID;
    //表名
//    @Column(name = "TableName")
    private String TableName;
    //字段名
//    @Column(name = "ColName")
    private String ColName;
//    @Column(name = "ColType")
    private String ColType;
//    @Column(name = "ColOrder")
    private Integer ColOrder;
//    @Column(name = "ColWidth")
    private Integer ColWidth;
//    @Column(name = "ColPrecision")
    private Integer ColPrecision;
//    @Column(name = "ColNull")
    private Integer ColNull;
//    @Column(name = "ColDefault")
    private String ColDefault;
//    @Column(name = "DisplayLabel")
    private String DisplayLabel;
//    @Column(name = "DisplayWidth")
    private Integer DisplayWidth;
//    @Column(name = "DisplayFormat")
    private String DisplayFormat;
//    @Column(name = "EditFormat")
    private String EditFormat;
//    @Column(name = "ColVarify")
    private String ColVarify;
//    @Column(name = "VarifyMsg")
    private String VarifyMsg;
//    @Column(name = "ColVisible")
    private Integer ColVisible;
//    @Column(name = "ColProperty")
    private Integer ColProperty;
//    @Column(name = "ColGroup")
    private String ColGroup;
//    @Column(name = "enus")
    private String enus;
//    @Column(name = "zhtw")
    private String zhTw;
//    @Column(name = "OtherLanguage")
    private String OtherLanguage;
//    @Column(name = "PopupWindow")
    private String PopupWindow;
//    @Column(name = "ColSaveChanged")
    private String ColSaveChanged;
//    @Column(name = "ColTag")
    private String ColTag;
//    @Column(name = "MinValue")
    private String MinValue;
//    @Column(name = "MaxValue")
    private String MaxValue;
//    @Column(name = "Encrypt")
    private Integer Encrypt = 0;
//    @Column(name = "GridShowMaxLen")
    private Integer GridShowMaxLen = 0;
//    @Column(name = "QuerySql")
    private String QuerySql;
//    @Column(name = "QuerySqlTarget")
    private String QuerySqlTarget;
//    @Column(name = "QuerySqlRight")
    private Integer QuerySqlRight = 0;
//    @Column(name = "NewRow")
    private Integer NewRow = 0;
//    @Column(name = "MultiSel")
    private String MultiSel = "0";
//    @Column(name = "AppScope")
    private String AppScope;




}