package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
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
@RequestMapping("/api/City")
@Api(tags = {""},description = "")
@Slf4j
public class CityController extends BaseController{

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
    ISchoolService schoolService;

    @Autowired
    ISysDicService sysDicService;


    @RequestMapping(value = "getAllCity", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.get("id", String.class);
        String user_code = claims.get("user_code", String.class);
        String user_role = claims.get("user_role", String.class);
        if (!user_code.equals("999") && !user_code.equals("998") && !menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }

        return this.renderSuccess();
    }


}
