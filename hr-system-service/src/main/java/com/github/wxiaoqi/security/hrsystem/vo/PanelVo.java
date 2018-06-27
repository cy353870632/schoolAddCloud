package com.github.wxiaoqi.security.hrsystem.vo;

import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import lombok.Data;

/**
 *
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class PanelVo extends WebColumns {

    //表名+字段名
    public String colAliasName;

    public String label;

    public PanelDataVo data;

    public  Boolean colReadOnly = false;

    public  Boolean isEncrypt = false;

    public int colRight = 1;
    public Boolean enabled = true;
    public String type ="TextBox";

    public int tableTypeID = 27;


}
