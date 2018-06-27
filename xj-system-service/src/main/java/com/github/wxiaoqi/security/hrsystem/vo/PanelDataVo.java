package com.github.wxiaoqi.security.hrsystem.vo;

import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class PanelDataVo {

    //表名+字段名
    public Boolean checked = false;
    public List items;
    public String newValue;
    public String oldValue;
    public String selectedValue;
    public String selectedValues;
    public String text;
    public String value;
    public String values;
    public String time;


}
