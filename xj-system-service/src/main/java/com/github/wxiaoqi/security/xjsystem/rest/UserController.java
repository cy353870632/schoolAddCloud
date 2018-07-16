package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@RequestMapping("/api/User")
@Api(tags = {""},description = "")
@Slf4j
public class UserController extends BaseController{

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

    @Autowired
    RoleMapper roleMapper;


    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    public Object getMenu(HttpServletRequest request) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        UserInfoVo userInfoVo = userService.getInfo(claims);
        Map restltMap = new HashMap<>();
        restltMap.put("userInfo",userInfoVo);
        restltMap.put("menus",menuService.getMenu(user_role));
        return this.renderSuccess(restltMap);
    }

    @RequestMapping(value = "getPromoterList", method = RequestMethod.POST)
    public Object getPromoterList(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        Boolean status = false;
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_id = claims.get("id", String.class);
        Map cacheMap = cacheService.cacheGridMessage("getmenu",user_role);
        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
        if (menuObejct != null && menuObejct.size()>0) {
            if (menuObejct.get(0).getClass().equals(JSONObject.class)){
                for (JSONObject map : menuObejct) {
                    List<JSONObject> childMapList = (List<JSONObject>)map.get("children");
                    if (childMapList != null && childMapList.size()>0) {
                        for (JSONObject child : childMapList) {
                            if (child.get("title").equals("推广员管理")) {
                                status = true;
                                break;
                            }
                        }
                    }
                }
            }else
            {
                for (Object map : menuObejct) {
                        List<MenuVo> childMapList = ((MenuVo) map).getChildren();
                        if (childMapList != null && childMapList.size()>0) {
                            for (MenuVo child : childMapList) {
                                if (child.getTitle().equals("推广员管理")) {
                                    status = true;
                                    break;
                                }
                            }
                        }
                }
            }
        }
        if (status){
            if (pageSize == null){
                pageSize = 10;
            }
            if (currentPage == null){
                currentPage = 1;
            }
            List<User> promoterList = userService.getPromoter(user_id,keyWord,pageSize,currentPage);
            Integer total = userService.getPromoterTotal(user_id,keyWord);
            Pageable pageable = new Pageable();
            pageable.setCurrentPage(currentPage);
            pageable.setPageSize(pageSize);
            pageable.setTotal(total);
            return this.renderSuccess(promoterList,pageable);
        }
        return this.renderError("您没有访问该页面的权限","400");//权限不够
    }

    @RequestMapping(value = "addPromoter", method = RequestMethod.POST)
    public Object addPromoter(HttpServletRequest request,@RequestBody User user) throws Exception{
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_code = claims.get("user_code", String.class);
        String user_id = claims.get("id", String.class);
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作","400");//权限不够
        }
        if (userService.addPromoter(user)==1)
            return this.renderSuccess();
        else
            return this.renderError("保存失败","add is error");

    }

}
