package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.entity.WebModuleInfo;
import com.github.wxiaoqi.security.hrsystem.entity.WebParams;
import com.github.wxiaoqi.security.hrsystem.mapper.WebFuncFrameMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebModuleInfoMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebParamsMapper;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncAssignService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import com.github.wxiaoqi.security.hrsystem.service.IWebModuleInfoService;
import com.github.wxiaoqi.security.hrsystem.utils.FuncToDefaultVoUtils;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 模块表
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebModuleInfoServiceImpl extends ServiceImpl<WebModuleInfoMapper,WebModuleInfo> implements IWebModuleInfoService{


    @Autowired
    WebModuleInfoMapper WebModuleInfoMapper;

    @Autowired
    WebParamsMapper webParamsMapper;

    @Autowired
    WebFuncFrameMapper webFuncFrameMapper;

    @Autowired
    IWebFuncFrameService webFuncFrameService;

    @Override
    public List selectModeAndFramByA0188(Integer A0188) {
        List<WebFuncFrame> webFuncFrameList = webFuncFrameMapper.callWebGetFuncView("29",A0188);
        List<String> modeIdList = new ArrayList<>();
        for (WebFuncFrame webFuncFrame:webFuncFrameList){
            modeIdList.add(webFuncFrame.getModuleid());
        }
//        List<WebModuleInfo> webModuleInfoList = WebModuleInfoMapper.selectModeuleBymodeid(modeIdList);
        List<WebModuleInfo> webModuleInfoList = WebModuleInfoMapper.selectAllBymodeid();
        //查询模块url，工作桌面，经理平台，员工自助
        List<String> paramNamelist = new ArrayList<>();
        paramNamelist.add("DeskTopUrl");paramNamelist.add("MssModeDefaultPage");paramNamelist.add("EssModeDefaultPage");
        List<WebParams> webParamsList = webParamsMapper.selectWebParamsByparamName(paramNamelist);
        //第一级菜单
        List<DefaultVo> level1defaultVoList = copymodetoDefaultVo(webModuleInfoList,webParamsList);
        List<DefaultVo> level2 = new ArrayList<>();
        for (DefaultVo defaultVo : level1defaultVoList){
            List<DefaultVo> level3 = new ArrayList<>();
            List<DefaultVo> level2defaultVoList = FuncToDefaultVoUtils.copyfunFrametoDefaultVoLeave2(webFuncFrameList,defaultVo.getFuncCode(),"2");
            //过滤掉二级菜单的framlist
            if (level2defaultVoList.size()!=0){
                for (DefaultVo defaultVo1 : level2defaultVoList) {
                    List<DefaultVo> level3defaultVoList = FuncToDefaultVoUtils.copyfunFrametoDefaultVoLeave2(webFuncFrameList, defaultVo.getFuncCode(), "3");
                    defaultVo1.setChildren(level3defaultVoList);
                    level3.add(defaultVo1);
                }
            }
            defaultVo.setChildren(level3);
            level2.add(defaultVo);
        }
        return level2;
    }

    private List<DefaultVo> copymodetoDefaultVo(List<WebModuleInfo> webModuleInfoList,List<WebParams> webParamsList){
        List<DefaultVo> defaultVoList = new ArrayList<>();
        for (WebModuleInfo webModuleInfo:webModuleInfoList){
            DefaultVo defaultVo = new DefaultVo();
            defaultVo.setFuncCode(webModuleInfo.getModuleid());
            defaultVo.setFuncName(webModuleInfo.getModulename());
            defaultVo.setTag(null);
            defaultVo.setFa(webModuleInfo.getFa());
            defaultVo.setFunTag(1);
            defaultVo.setLevel("1");
            defaultVo.setFuncUrl(webModuleInfo.getUrl());
            //21员工自助，22，经理平台，29，工作桌面
            if (webModuleInfo.getModuleid().equals("21") ){
                List<WebParams> webParams = webParamsList.stream().filter(webParams1 -> webParams1.getParam_name().equals("EssModeDefaultPage")).collect(Collectors.toList());
                if (webParams.size()>0){
                    defaultVo.setFuncUrl(webParams.get(0).getParam_value());
                }else
                {
                    defaultVo.setFuncUrl("Desktop/EssDesktop.aspx");
                }
            }
            if (webModuleInfo.getModuleid().equals("22") ){
                List<WebParams> webParams = webParamsList.stream().filter(webParams1 -> webParams1.getParam_name().equals("MssModeDefaultPage")).collect(Collectors.toList());
                if (webParams.size()>0){
                    defaultVo.setFuncUrl(webParams.get(0).getParam_value());
                }else
                {
                    defaultVo.setFuncUrl("Desktop/MssDesktop.aspx");
                }
            }
            if (webModuleInfo.getModuleid().equals("29") ){
                List<WebParams> webParams = webParamsList.stream().filter(webParams1 -> webParams1.getParam_name().equals("DeskTopUrl")).collect(Collectors.toList());
                if (webParams.size()>0){
                    defaultVo.setFuncUrl(webParams.get(0).getParam_value());
                }else
                {
                    defaultVo.setFuncUrl("Desktop/WorkDesktop.aspx");
                }
            }
            defaultVoList.add(defaultVo);
        }
        return defaultVoList;
    }



}
