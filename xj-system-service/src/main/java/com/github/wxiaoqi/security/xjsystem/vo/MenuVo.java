package com.github.wxiaoqi.security.xjsystem.vo;

import lombok.Data;

import java.util.List;

/**
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class MenuVo {
    public String title;

    public String codePath;
    public String over_End;
    public List<MenuVo> children;


}
