package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements IMenuService{


    @Autowired
    MenuMapper menuMapper;


    private String passwordKey = "d+#8p&nn=o30ke6%-";


    @Override
    public Object getMenu(String role) {
        //多角色
        List roles = new ArrayList<>();
        if (role.contains(",")){
            String[] roleone = role.split(",");
            for (String r : roleone){
                roles.add(r);
            }
        }else
        {
            roles.add(role);
        }
        List<Menu> menuList = menuMapper.selectByRole(roles);
        List<MenuVo> menuVoList = new MenuUtil().getMenuTree(menuList);
        return menuVoList;
    }
}
