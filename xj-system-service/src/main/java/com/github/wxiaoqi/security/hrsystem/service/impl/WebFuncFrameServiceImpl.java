package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebFuncFrameMapper;
import com.github.wxiaoqi.security.hrsystem.service.*;
import com.github.wxiaoqi.security.hrsystem.utils.FuncToDefaultVoUtils;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 菜单fram类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebFuncFrameServiceImpl extends ServiceImpl<WebFuncFrameMapper,WebFuncFrame> implements IWebFuncFrameService{


    @Autowired
    WebFuncFrameMapper webFuncFrameMapper;

    @Autowired
    IWebFuncAssignService webFuncAssignService;


    @Override
    public List<WebFuncFrame> selectByAcotrid(Integer A0188) {
        List<String> funcCodeList = webFuncAssignService.selectFunccodeByAcotrid(A0188);
        if (funcCodeList.size()>2000){
            List<WebFuncFrame> webFuncFrameList1 = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList.subList(0,1000),"00");
            List<WebFuncFrame> webFuncFrameList2 = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList.subList(1001,funcCodeList.size()),"00");
            webFuncFrameList1.addAll(webFuncFrameList2);
            return webFuncFrameList1;
        }else{
            List<WebFuncFrame> webFuncFrameList = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList,"00");
            return webFuncFrameList;
        }
    }

    @Override
    public List<DefaultVo> selectBymodid(Integer A0188, String modeid) {
//        List<String> funcCodeList = webFuncAssignService.selectFunccodeByAcotrid(A0188);
//        if (funcCodeList.size()>2000){
//            List<WebFuncFrame> webFuncFrameList1 = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList.subList(0,1000),modeid);
//            List<WebFuncFrame> webFuncFrameList2 = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList.subList(1001,funcCodeList.size()),modeid);
//            webFuncFrameList.addAll(webFuncFrameList1);
//            webFuncFrameList.addAll(webFuncFrameList2);
//
//        }else{
//            webFuncFrameList = webFuncFrameMapper.selectByfunccodeAndmodid(funcCodeList,modeid);
//
//        }
        List<WebFuncFrame> webFuncFrameList = webFuncFrameMapper.callWebGetFuncView(modeid,A0188);

        if (webFuncFrameList.size()>0){
            List<DefaultVo> level = new ArrayList<>();
            List<DefaultVo> level2defaultVoList = FuncToDefaultVoUtils.copyfunFrametoDefaultVoLeave2(webFuncFrameList,modeid,"2");
            for (DefaultVo defaultVo:level2defaultVoList){
                    List<DefaultVo> level3defaultVoList = FuncToDefaultVoUtils.copyfunFrametoDefaultVoLeave2(webFuncFrameList,defaultVo.getFuncCode(),"3");
                    defaultVo.setChildren(level3defaultVoList);
                    level.add(defaultVo);


            }
            return level;
        }
        return null;
    }

}
