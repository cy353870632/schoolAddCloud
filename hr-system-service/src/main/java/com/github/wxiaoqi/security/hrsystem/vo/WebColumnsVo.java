package com.github.wxiaoqi.security.hrsystem.vo;

import lombok.Data;

/**
 *
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class WebColumnsVo {

    //表名+字段名
    public String prop;
    //displaylabel
    public String label;
    //displaywidth*100+px
    public String width;
    //editformat,如果不是check和code和time和color和link类型，剩下的都是literal
    private String type;

}
