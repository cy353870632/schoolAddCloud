package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IRoleService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements IRoleService{
    @Autowired
    RoleMapper roleMapper;


    @Override
    @Cache(key = "getroleByrname:u{1}")
    public Role getRoleByRname(String r_name) {
        Role role = roleMapper.getRoleByRname(r_name);
        return role;
    }
}
