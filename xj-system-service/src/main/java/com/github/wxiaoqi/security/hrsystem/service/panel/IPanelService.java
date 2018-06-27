package com.github.wxiaoqi.security.hrsystem.service.panel;


import com.github.wxiaoqi.security.hrsystem.entity.PanelOpt;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IPanelService {

    public Object getPaneByTableNameAndKid(String tablename,String primaryKey,int k_id,int A0188,String userName);

    public String getpanelKey( List<Map> groups,List<PanelVo> hiddenFields);

    public Boolean updatepanel(PanelOpt panelOpt, Integer A0188);

    public Boolean savepanel(PanelOpt panelOpt, Integer A0188);


}
