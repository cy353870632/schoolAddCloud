package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements IMenuService{


    @Autowired
    MenuMapper menuMapper;


    private String passwordKey = "d+#8p&nn=o30ke6%-";

    @Autowired
    ICacheService cacheService;

    @Autowired
    IRoleService roleService;

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

    @Override
    public Integer upMenu(Menu menu,Integer status) {
        Menu menu1 = menuMapper.selectById(menu.getId());
        if (status==1) {
            if (menu.getTitle() != null && !menu.getTitle().equals(""))
                menu1.setTitle(menu.getTitle());
            if (menu.getParent_id() != null && !menu.getParent_id().equals("")) {
                menu1.setParent_title(menu.getParent_id());
                Menu menu2 = menuMapper.selectById(menu.getParent_id());
                menu1.setParent_title(menu2.getParent_title());
            }
            if (menu.getCode_path() != null&& !menu.getCode_path().equals(""))
                menu1.setCode_path(menu.getCode_path());
            menu1.setStatus("1");
        }else if (status==0){
            menu1.setStatus("0");
        }else
        {
            menu1.setStatus("2");//冻结
        }
        cacheService.clearCache("getAllmenuParent","Parent");
        EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
        wrapper.eq("id",menu1.getId());
        return menuMapper.update(menu1,wrapper);
    }

    @Override
    @Cache(key = "allMenu")
    public List<Menu> getAllMenu() {
        EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
        wrapper.eq("status",1);
        wrapper.eq("end_mark","1");
        return menuMapper.selectList(wrapper);
    }

    //按照角色权限的规则，来处理结果
    @Override
    public Object roleToMenuCheck(List<Menu> menuList,List<String> menuCheckList,Integer status) {
        //status为0是：999给999和998授权，1是999给其他授权，2是998给别人授权
        List<Map> menuAllList = new ArrayList<>();
        switch (status){
            case 0:
                for (Menu menu:menuList){
                    Map map = new HashMap<>();
                    map.put("key",menu.getId());
                    map.put("label",menu.getTitle());
                    map.put("disabled",false);
                    menuAllList.add(map);
                }
                break;
            case 1:case 2:
                for (Menu menu:menuList){
                    if (!menu.getParent_title().equals("系统设置") && !menu.getCode_path().equals("promoterMange")
                            && !menu.getCode_path().equals("userMange")) {
                        Map map = new HashMap<>();
                        map.put("key", menu.getId());
                        map.put("label", menu.getTitle());
                        map.put("disabled", false);
                        menuAllList.add(map);
                    }
                }
                break;
        }
        Map resultMap = new HashMap<>();
        resultMap.put("allMenu",menuAllList);
        resultMap.put("checkMenu",menuCheckList);
        return resultMap;
    }

    //获取该role可以被授权的所有的菜单
    @Override
    @Cache(key = "allMenuByRole:u{1}")
    public List<Menu> getAllMenuByRole(String roleid) {
        Role role = roleService.selectById(roleid);
        EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
        wrapper.eq("status",1);
        wrapper.eq("end_mark","1");
        List<Menu> menuList = menuMapper.selectList(wrapper);
        if (role.getRole_code()==999||role.getRole_code()==998){
            return menuList;
        }
        else {
            menuList = menuList.stream().filter(f->!f.getParent_title().equals("系统设置")&&!f.getCode_path().equals("promoterMange")
                    &&!f.getCode_path().equals("userMange")).collect(Collectors.toList());
        }
        return menuList;
    }

    @Override
    @CacheClear(key = "allMenu")
    public void clearAllMneuCache() {
        log.info("清除了缓存 allMenu");
    }

    @Override
    @CacheClear(key = "allMenuByRole:u{1}")
    public void clearMenuByRoleCache(Role role) {
        log.info("清除了缓存 "+role.getR_name()+"  的可用菜单");
    }
}
