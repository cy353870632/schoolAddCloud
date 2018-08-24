package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
import java.util.UUID;

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

    @Override
//    @Cache(key = "getAllRole")
    public List<Role> getAllRole(String keyword, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<Role> result = roleMapper.selectAllRole(keyword,currentPage,pageSize);
        return result;
    }

    @Override
    public Integer getAllRoleTotal(String keyword) {
        return roleMapper.selectAllRoletotal(keyword);
    }

    @Override
    public Integer addRole(Role role) {
        role.setRead_only("0");
        role.setCreat_date(new Date());
        role.setUpdate_date(new Date());
        role.setStatus(1);
        role.setId(UUID.randomUUID().toString());
        if (role.getSort()==null)
            role.setSort("0");
        return roleMapper.insert(role);
    }

    @Override
    public Integer upRole(Role role,Role oldrole) {
        if (role.getR_name()!=null && !role.getR_name().equals(""))
            oldrole.setR_name(role.getR_name());
        if (role.getR_name_china()!=null && !role.getR_name_china().equals(""))
            oldrole.setR_name_china(role.getR_name_china());
        if (role.getSort()!=null && !role.getSort().equals(""))
            oldrole.setSort(role.getSort());
        oldrole.setUpdate_date(new Date());
        return roleMapper.updateById(oldrole);
    }
}
