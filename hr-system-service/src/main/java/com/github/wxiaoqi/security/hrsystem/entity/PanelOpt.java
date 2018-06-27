package com.github.wxiaoqi.security.hrsystem.entity;

import com.github.wxiaoqi.security.hrsystem.vo.PanelInterface;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PanelOpt {
    private String panelKey; //不知道什么规则
    //工号，账号
    private Boolean allowGroup;

    private Boolean enabled;

    private List<Map> groups;

    private List<PanelVo> hiddenFields;

    private PanelInterface panelInterface;

    private Map updateTables;



}