package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.DocUtil;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.ResultVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/Menu")
@Api(tags = {""},description = "")
@Slf4j
public class MenuController extends BaseController{

    @Autowired
    IUserService userService;

    @Value("${user.token-header}")
    private String tokenHeader;

    //token过期时间
    @Value("${jwt.expire}")
    private long ttlMillis;

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    ICacheService cacheService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IMenuService menuService;

    @RequestMapping(value = "getMenu", method = RequestMethod.POST)
    public Object getMenu(HttpServletRequest request) throws Exception {
        String user_role = UserMessage.getUserRole();
        return this.renderSuccess(menuService.getMenu(user_role));
    }

    @RequestMapping(value = "getAllMenu", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        if (currentPage==0){
            currentPage =1;
        }
        if (pageSize==0){
            pageSize = 10;
        }
        List<Menu> menuList = menuService.getAllMenu(keyWord,pageSize,currentPage);
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal(menuService.getMenuTotal(keyWord));
        DocUtil.saveDoc("获取可用菜单","普通");

        return this.renderSuccess(menuList,pageable);
    }

    @RequestMapping(value = "addMenu", method = RequestMethod.POST)
    public Object addMenu(HttpServletRequest request,@RequestBody Menu menu) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        try {
            if (menuService.addMenu(menu)==1) {
                DocUtil.saveDoc("新增菜单成功", "敏感");
                return this.renderSuccess();
            }
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级菜单下已经存在该子菜单，请勿重复添加",201);
            if (s2.equals("parentTile_codePath"))
                return this.renderError("该父级菜单下已经存在该跳转路由，请勿重复添加",201);
            else
                return this.renderError("添加失败,请检查填写信息重试",201);
        }

    }


    @RequestMapping(value = "getParentMenu", method = RequestMethod.POST)
    public Object getParentMenu() throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        return this.renderSuccess(menuService.getParentMenu());
    }

    @RequestMapping(value = "getMenuByid", method = RequestMethod.POST)
    public Object getMenuByid(HttpServletRequest request,String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        return this.renderSuccess(menuService.selectById(id));
    }

    @RequestMapping(value = "upMenu", method = RequestMethod.POST)
    public Object upMenu(HttpServletRequest request,@RequestBody Menu menu) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        try {
            if (menuService.upMenu(menu,1)==1){
                DocUtil.saveDoc("更新菜单成功","敏感");
                return this.renderSuccess();
            }
            else
                return this.renderError("更新失败",201);
        }catch (Exception e){
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级菜单下已经存在该子菜单",201);
            if (s2.equals("parentTile_codePath"))
                return this.renderError("该父级菜单下已经存在该跳转路由",201);
            else
                return this.renderError("更新失败,请检查填写信息重试",201);
        }
    }
    @RequestMapping(value = "deleteMenu", method = RequestMethod.POST)
    public Object deleteMenu(HttpServletRequest request,String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        Menu menu = new Menu();
        menu.setId(id);
        if (menuService.upMenu(menu,0)==1)
        {
            DocUtil.saveDoc("删除菜单成功","敏感");
            return this.renderSuccess();
        }
        else
            return this.renderError("删除失败",201);
    }
    @RequestMapping(value = "blockMenu", method = RequestMethod.POST)
    public Object blockMenu(HttpServletRequest request,String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        Menu menu = new Menu();
        menu.setId(id);
        if (menuService.upMenu(menu,2)==1){
            DocUtil.saveDoc("冻结菜单成功","敏感");
            return this.renderSuccess();
        }
        else
            return this.renderError("冻结失败",201);
    }
    @RequestMapping(value = "unblockMenu", method = RequestMethod.POST)
    public Object unblockMenu(HttpServletRequest request,String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        Menu menu = new Menu();
        menu.setId(id);
        if (menuService.upMenu(menu,1)==1){
            DocUtil.saveDoc("解冻菜单成功","敏感");
            return this.renderSuccess();
        }
        else
            return this.renderError("生效失败",201);
    }
}
