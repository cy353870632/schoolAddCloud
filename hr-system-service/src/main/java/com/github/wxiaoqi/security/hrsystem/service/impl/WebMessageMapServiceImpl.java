package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebMessageMapMapper;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebMessageMapService;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 消息服务类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebMessageMapServiceImpl extends ServiceImpl<WebMessageMapMapper,WebMessageMap> implements IWebMessageMapService{


    @Autowired
    WebMessageMapMapper webMessageMapMapper;


    @Override
    public List<WebMessageMap> selectByA0188(int A0188) {
        List<WebMessageMap> webMessageMapList = webMessageMapMapper.selectByA0188(A0188);
        return webMessageMapList;
    }
}
