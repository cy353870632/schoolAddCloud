package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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


    @Autowired
    ICacheService cacheService;

    @Override
    public List<MenuVo> getMenu(String role) {
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
        List<MenuVo> menuVoList = MenuUtil.getMenuTree(menuList);
        cacheService.clearCache("getmenu",role);
        cacheService.getCacheMessage(menuVoList);
        cacheService.cacheGridMessage("getmenu",role);
        return menuVoList;
    }

    @Override
    public Boolean checkMenu(String user_role, String MenuCodePath) {
        Boolean status = false;
        Map cacheMap = cacheService.cacheGridMessage("getmenu",user_role);
        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
        if (menuObejct != null && menuObejct.size()>0) {
            try {
                for (JSONObject map : menuObejct) {
                    List<JSONObject> childMapList = (List<JSONObject>)map.get("children");
                    if (childMapList != null && childMapList.size()>0) {
                        for (JSONObject child : childMapList) {
                            if (child.get("codePath").equals(MenuCodePath)) {
                                status = true;
                                break;
                            }
                        }
                    }
                }
            }catch (Exception e){
                for (Object map : menuObejct) {
                    List<MenuVo> childMapList = ((MenuVo) map).getChildren();
                    if (childMapList != null && childMapList.size()>0) {
                        for (MenuVo child : childMapList) {
                            if (child.getCodePath().equals(MenuCodePath)) {
                                status = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return status;
    }

    @Override
    public List getAllMenu(String keyword, Integer pageSize, Integer currentPage) {

//        Map cacheMap = cacheService.cacheGridMessage("getAllmenu",pageSize+currentPage);
//        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
//        if (menuObejct == null){
            currentPage = (currentPage-1)*pageSize;
            List<Menu> menuList = menuMapper.selectAllMenu(keyword,pageSize,currentPage);
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
//            cacheService.getCacheMessage(menuList);
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
            return menuList;
//        }
//        return menuObejct;
    }

    @Override
    public Integer getMenuTotal(String keyword) {
        Integer total = menuMapper.selectMenuTotal(keyword);
        return total;
    }

    @Override
    public Integer addMenu(Menu menu) {
        menu.setCreat_date(new Date());
        if (menu.getParent_id().equals("0")){
            menu.setEnd_mark("0");
        }
        else{
            menu.setEnd_mark("1");
            Menu menu1 = menuMapper.selectById(menu.getParent_id());
            menu.setParent_title(menu1.getTitle());
        }
        menu.setUpdate_date(new Date());
        menu.setStatus("1");
        menu.setRead_only("1");
        menu.setId(UUID.randomUUID().toString());
        Integer status = menuMapper.insert(menu);
        cacheService.clearCache("getAllmenuParent","Parent");
        return status;
    }

    @Override
    public List getParentMenu() {
        Map cacheMap = cacheService.cacheGridMessage("getAllmenuParent","Parent");
        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
        if (menuObejct == null){
            List<Menu> menuList_parent = menuMapper.selectAllParent();
            cacheService.clearCache("getAllmenuParent","Parent");
            cacheService.getCacheMessage(menuList_parent);
            cacheService.cacheGridMessage("getAllmenuParent","Parent");
            return menuList_parent;
        }
        return menuObejct;
    }
}
