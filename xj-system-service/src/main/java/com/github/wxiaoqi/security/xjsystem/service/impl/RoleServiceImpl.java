package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.MenuRole;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.*;
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

    @Autowired
    IMenuService menuService;

    @Autowired
    IMenuRoleService menuRoleService;

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
        if (role.getRole_code()!=0)
            oldrole.setRole_code(role.getRole_code());
        oldrole.setUpdate_date(new Date());
        return roleMapper.updateById(oldrole);
    }

    @Override
    public Boolean upRoleMenu(Role role,List<String> menuId) {
        //可以被授权的所有的菜单
        List<Menu> allMenuList = menuService.getAllMenuByRole(role.getId());
        List<String> allMenuIdList = new ArrayList<>();
        for (Menu menu:allMenuList){
            allMenuIdList.add(menu.getId());
        }
        //已经被授权的所有的菜单
        List<String> exitsMenuIdList = menuRoleService.getMenuIdByRoleId(role.getId());
        for (String s:menuId){
            if (allMenuIdList.contains(s) && !exitsMenuIdList.contains(s)){
                //新增
                MenuRole menuRole = new MenuRole();
                menuRole.setId(UUID.randomUUID().toString());
                menuRole.setMenu_id(s);
                menuRole.setRole_id(role.getId());
                menuRole.setRead_only("0");
                menuRole.setCreat_date(new Date());
                menuRole.setUpdate_date(new Date());
                menuRoleService.insert(menuRole);
                exitsMenuIdList.add(s);
            }
        }
        for (String s:exitsMenuIdList){
            if (!menuId.contains(s)){
                //删除
                EntityWrapper<MenuRole> wrapper = new EntityWrapper<MenuRole>();
                wrapper.eq("menu_id",s);
                wrapper.eq("role_id",role.getId());
                menuRoleService.delete(wrapper);
            }
        }
        menuService.clearMenuByRoleCache(role);
        return true;
    }

    @Override
    public Boolean checkRoleStatus(String roleId) {
        EntityWrapper<Role> wrapper = new EntityWrapper<Role>();
        wrapper.eq("id",roleId);
        wrapper.eq("status","1");
        if (roleMapper.selectCount(wrapper)==0)
            return false;
        return true;
    }
}
