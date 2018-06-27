package com.github.wxiaoqi.security.hrsystem.utils;

import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-24 16:53
 * @desc 模块类转vo输出
 **/
public class FuncToDefaultVoUtils {

    public static List<DefaultVo> copyfunFrametoDefaultVoLeave2(List<WebFuncFrame> webFuncFrameList, String modeId, String level){
        List<DefaultVo> defaultVoList = new ArrayList<>();
        for (WebFuncFrame webFuncFrame:webFuncFrameList){
            if (webFuncFrame.getFuncparent().equals(modeId)){
                DefaultVo defaultVo = new DefaultVo();
                defaultVo.setFuncCode(webFuncFrame.getFunccode());
                defaultVo.setFuncName(webFuncFrame.getFuncname());
                defaultVo.setTag("");
                defaultVo.setFa(webFuncFrame.getFa());
                defaultVo.setFunTag(webFuncFrame.getFuntag());
                defaultVo.setLevel(level);
                defaultVo.setFuncUrl(webFuncFrame.getFuncurl());
                defaultVoList.add(defaultVo);
            }
        }
        return defaultVoList;
    }

}
