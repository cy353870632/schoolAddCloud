package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Area;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.mapper.AreaMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.CityMapper;
import com.github.wxiaoqi.security.xjsystem.service.IAreaService;
import com.github.wxiaoqi.security.xjsystem.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 区
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl extends ServiceImpl<AreaMapper,Area> implements IAreaService{
    @Autowired
    AreaMapper areaMapper;


}
