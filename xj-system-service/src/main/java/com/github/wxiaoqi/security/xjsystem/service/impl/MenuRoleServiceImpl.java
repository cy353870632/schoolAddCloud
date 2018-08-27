package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.entity.MenuRole;
import com.github.wxiaoqi.security.xjsystem.mapper.CityMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuRoleMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICityService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 市
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper,MenuRole> implements IMenuRoleService{
    @Autowired
    MenuRoleMapper menuRoleMapper;


    @Override
    public List<String> getMenuIdByRoleId(String roleId) {
        EntityWrapper<MenuRole> wrapper = new EntityWrapper<MenuRole>();
        wrapper.eq("role_id",roleId);
        List<MenuRole> menuRoleList = menuRoleMapper.selectList(wrapper);
        List<String> result = new ArrayList<>();
        for (MenuRole menuRole:menuRoleList){
            result.add(menuRole.getMenu_id());
        }
        return result;
    }
}
