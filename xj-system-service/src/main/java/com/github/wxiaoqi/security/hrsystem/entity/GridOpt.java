package com.github.wxiaoqi.security.hrsystem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class GridOpt {
    private String gridKey; //缓存GridOpt的key 接口类名+方法名
    //工号，账号
    private String keyField;//表名+主键

    private String CommandText; // 传入sql

    private Boolean AllowPaging ; // 是否允许分页，不允许则设置为醉倒100000

    private Integer PageSize;

    private Integer tableName;

    private String DataKeyField ; // 可能是需要吧这个主键的cloms给删除掉，不向外展示

}