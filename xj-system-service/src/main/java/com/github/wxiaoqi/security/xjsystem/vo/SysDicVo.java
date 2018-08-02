package com.github.wxiaoqi.security.xjsystem.vo;

import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import lombok.Data;

import java.util.List;

/**
 * 工作桌面vo
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class SysDicVo extends System_dic{
    public List<System_dic> children;
}
