package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.mapper.CityMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.ProvinceMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICityService;
import com.github.wxiaoqi.security.xjsystem.service.IProviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 市
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl extends ServiceImpl<CityMapper,City> implements ICityService{
    @Autowired
    CityMapper cityMapper;


}
