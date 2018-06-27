package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.WebActorAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebActorAssignMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebFuncFrameMapper;
import com.github.wxiaoqi.security.hrsystem.service.IWebActorAssignService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户和权限关联表
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebActorAssignServiceImpl extends ServiceImpl<WebActorAssignMapper,WebActorAssign> implements IWebActorAssignService{

    @Autowired
    WebActorAssignMapper webActorAssignMapper;

    @Override
    public List<Integer> selectActoridByA0188(Integer A0188) {
        List<Integer> WebActorAssignlist = webActorAssignMapper.selectAcordidByA0188(A0188);
        return WebActorAssignlist;
    }
}
