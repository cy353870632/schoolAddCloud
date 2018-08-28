package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.MenuRole;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/Role")
@Api(tags = {""},description = "")
@Slf4j
public class RoleController extends BaseController{

    @Autowired
    IUserService userService;

    @Value("${user.token-header}")
    private String tokenHeader;

    //token过期时间
    @Value("${jwt.expire}")
    private long ttlMillis;


    @Autowired
    ICacheService cacheService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IMenuService menuService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IMenuRoleService menuRoleService;

//    @RequestMapping(value = "getRole", method = RequestMethod.POST)
//    public Object getMenu(HttpServletRequest request) throws Exception {
//        String user_role = UserMessage.getUserRole();
//        return this.renderSuccess(menuService.getMenu(user_role));
//    }

    @RequestMapping(value = "getAllRole", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"roleManage")){
            return this.renderError("访问权限不够",400);
        }
        if (currentPage==0){
            currentPage =1;
        }
        if (pageSize==0){
            pageSize = 10;
        }
        List<Role> roleResult = roleService.getAllRole(keyWord,pageSize,currentPage);
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal(roleService.getAllRoleTotal(keyWord));
        return this.renderSuccess(roleResult,pageable);
    }

    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    public Object addMenu(HttpServletRequest request,@RequestBody Role role) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"roleManage")){
            return this.renderError("访问权限不够",400);
        }
        if (role.getR_name()==null||role.getR_name().equals("")||role.getR_name_china()==null||role.getR_name_china().equals("")){
            return this.renderError("字段缺失",500);
        }
        try {
            if (roleService.addRole(role)==1)
                return this.renderSuccess();
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("r_name"))
                return this.renderError("该权限code已经存在，请勿重复添加",201);
            if (s2.equals("r_name_china"))
                return this.renderError("该权限名称已经存在，请勿重复添加",201);
            else
                return this.renderError("添加失败,请检查填写信息重试",201);
        }

    }
        @RequestMapping(value = "getRoleById", method = RequestMethod.POST)
    public Object getRoleById(String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"roleManage")){
            return this.renderError("访问权限不够",400);
        }
            Role role = roleService.selectById(id);
        if (role==null)
            return this.renderError("删除失败",201);
        else
            return this.renderSuccess(role);
        }
    @RequestMapping(value = "upRole", method = RequestMethod.POST)
    public Object upRole(@RequestBody Role role) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role, "roleManage")) {
            return this.renderError("访问权限不够", 400);
        }
        Role role1 = roleService.selectById(role.getId());
        if (role1 == null) {
            return this.renderError("找不到该条权限数据信息", 400);
        } else {
            try {
                if (roleService.upRole(role,role1) == 1)
                    return this.renderSuccess();
                else
                    return this.renderError("更新失败", 201);
            } catch (Exception e) {
                String s2 = StringUtils.subString(e.getCause().getMessage(), "for key '", "'");
                if (s2.equals("r_name"))
                    return this.renderError("该权限code已经存在，请勿重复添加",201);
                if (s2.equals("r_name_china"))
                    return this.renderError("该权限名称已经存在，请勿重复添加",201);
                else
                    return this.renderError("添加失败,请检查填写信息重试",201);
            }
        }
    }

    @RequestMapping(value = "deleteRole", method = RequestMethod.POST)
    public Object deleteMenu(String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        Role role = roleService.selectById(id);
        if (role==null)
            return this.renderError("删除失败",201);
        else {
            role.setStatus(0);
            if (roleService.updateById(role))
                return this.renderSuccess();
            else
                return this.renderError("删除失败",201);
        }
    }

    @RequestMapping(value = "getRoleMenuMessage", method = RequestMethod.POST)
    public Object getRoleMenuMessage(String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"roleManage")){
            return this.renderError("访问权限不够",400);
        }
        //超级管理员可以给998授权系统设置，给别的角色不可以设置系统设置
        Role role = roleService.selectById(id);
        List<Menu> menuList = menuService.getAllMenu();
        //获取用户现有的权限id列表
        List<String> menuCheckList = menuRoleService.getMenuIdByRoleId(id);
        if (menuList.size()==0){
            return this.renderError("没有可用权限进行授权",400);
        }
        if (user_code.equals("999")){
            if (role.getR_name().equals("ADMIN") || role.getR_name().equals("SYSADMIN")){
                return this.renderSuccess( menuService.roleToMenuCheck(menuList,menuCheckList,0));
            }else {
                return this.renderSuccess( menuService.roleToMenuCheck(menuList,menuCheckList,1));

            }
        }else {
            //如果是998的，吧可以授权系统设置，给除了998以外的，不可以设置推广员，管理员的人员设置
            if (role.getR_name().equals("ADMIN") || role.getR_name().equals("SYSADMIN")){
                //不可以下级给上级，同级授权
                return this.renderError("访问权限不够",400);
            }else {
                //除了系统管理，推广员，管理员设置
                return this.renderSuccess( menuService.roleToMenuCheck(menuList,menuCheckList,2));
            }
        }
    }
    @RequestMapping(value = "checkMenuToRole", method = RequestMethod.POST)
    public Object checkMenuToRole(String id, String menuList) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"roleManage")){
            return this.renderError("访问权限不够",400);
        }
        List<String> menuList1 = (List) JSON.parse(menuList);
        Role role = roleService.selectById(id);
        if (role==null){
            return this.renderError("没有该权限信息",400);
        }
        if (Integer.valueOf(user_code).intValue() < Integer.valueOf(role.getRole_code()).intValue()){
            return this.renderError("操作权限不够",400);
        }
        try {
            if (roleService.upRoleMenu(role,menuList1)){
                return this.renderSuccess();
            }
        }catch (Exception e){
            return this.renderError("授权失败:"+e.getMessage(),201);
        }
        return this.renderError("授权失败",201);
    }


}
