package com.github.wxiaoqi.security.xjsystem.vo;

import lombok.Data;

import java.util.List;

/**
 * 工作桌面vo
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class DefaultVo{
    //id WebfuncFrame func_code
    public String funcCode;
    //WebfuncFrame func_name
    public String funcName;
    //WebfuncFrame 里的func_Url ，路由
    public String funcUrl;
    //暂时没有用的，为null
    private String tag;
    //WebfuncFrame 里的fa
    private String fa;
    //WebfuncFrame 里的funTag
    private Integer funTag;;
    //菜单级别
    private String level;
    //子菜单
    private List<DefaultVo> children;
}
