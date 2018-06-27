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
public class PanelInterface{

    //表名+字段名
    public Boolean allowColGroup = true;
    public int colsInRow = 2;
    public Boolean customOrder = false;
    public Boolean enableSaveReadOnlyField = true;
    public Boolean enabled = true;
    public Boolean rightControl = false;
    public int rowHeight = 30;
    public int tableWidth = 700;


}
