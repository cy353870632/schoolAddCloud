package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import io.jsonwebtoken.Claims;
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
@RequestMapping("/api/School")
@Api(tags = {""},description = "")
@Slf4j
public class SchoolController extends BaseController{

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

    @RequestMapping(value = "getAllSchool", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.get("id", String.class);
        String user_code = claims.get("user_code", String.class);
        String user_role = claims.get("user_role", String.class);
        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
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
        return this.renderSuccess(menuList,pageable);
    }

    @RequestMapping(value = "addSchool", method = RequestMethod.POST)
    public Object addMenu(HttpServletRequest request,@RequestBody Menu menu) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.get("id", String.class);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        try {
            if (menuService.addMenu(menu)==1)
                return this.renderSuccess();
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


    @RequestMapping(value = "getSchoolByid", method = RequestMethod.POST)
    public Object getMenuByid(HttpServletRequest request,String id) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        return this.renderSuccess(menuService.selectById(id));
    }

    @RequestMapping(value = "upSchool", method = RequestMethod.POST)
    public Object upMenu(HttpServletRequest request,@RequestBody Menu menu) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
            return this.renderError("访问权限不够",400);
        }
        try {
            if (menuService.upMenu(menu,1)==1)
                return this.renderSuccess();
            else
                return this.renderError("更新失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级菜单下已经存在该子菜单",201);
            if (s2.equals("parentTile_codePath"))
                return this.renderError("该父级菜单下已经存在该跳转路由",201);
            else
                return this.renderError("更新失败,请检查填写信息重试",201);
        }
    }
    @RequestMapping(value = "deleteSchool", method = RequestMethod.POST)
    public Object deleteMenu(HttpServletRequest request,String id) throws Exception {
//        String token = request.getHeader(tokenHeader);
//        Claims claims = jwtUtil.parseJWT(token);
//        String user_role = claims.get("user_role", String.class);
//        String user_code = claims.get("user_code", String.class);
//        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        Menu menu = new Menu();
//        menu.setId(id);
//        if (menuService.upMenu(menu,0)==1)
//            return this.renderSuccess();
//        else
            return this.renderError("删除失败",201);
    }
//    @RequestMapping(value = "blockMenu", method = RequestMethod.POST)
//    public Object blockMenu(HttpServletRequest request,String id) throws Exception {
//        String token = request.getHeader(tokenHeader);
//        Claims claims = jwtUtil.parseJWT(token);
//        String user_role = claims.get("user_role", String.class);
//        String user_code = claims.get("user_code", String.class);
//        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        Menu menu = new Menu();
//        menu.setId(id);
//        if (menuService.upMenu(menu,2)==1)
//            return this.renderSuccess();
//        else
//            return this.renderError("冻结失败",201);
//    }
//    @RequestMapping(value = "unblockMenu", method = RequestMethod.POST)
//    public Object unblockMenu(HttpServletRequest request,String id) throws Exception {
//        String token = request.getHeader(tokenHeader);
//        Claims claims = jwtUtil.parseJWT(token);
//        String user_role = claims.get("user_role", String.class);
//        String user_code = claims.get("user_code", String.class);
//        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        Menu menu = new Menu();
//        menu.setId(id);
//        if (menuService.upMenu(menu,1)==1)
//            return this.renderSuccess();
//        else
//            return this.renderError("生效失败",201);
//    }
}
