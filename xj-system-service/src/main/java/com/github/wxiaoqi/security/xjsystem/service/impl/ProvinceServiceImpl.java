package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.mapper.ProvinceMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.service.IProviceService;
import com.github.wxiaoqi.security.xjsystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper,Province> implements IProviceService{
    @Autowired
    ProvinceMapper provinceMapper;


}
