package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IRoleService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
