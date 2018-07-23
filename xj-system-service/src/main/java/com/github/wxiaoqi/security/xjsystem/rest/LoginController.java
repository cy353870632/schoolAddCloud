package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.vo.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.xjsystem.vo.ResultVo;
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
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/TokenAuth")
@Api(tags = {""},description = "")
@Slf4j
public class LoginController extends BaseController{

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

    private String passwordKey = "d+#8p&nn=o30ke6%-";


    @RequestMapping(value = "Authenticate", method = RequestMethod.POST)
    public Object createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletRequest request) throws Exception {
        log.info(authenticationRequest.getUserNameOrEmailAddress()+" require logging...");
        User user = userService.getUserInfo(authenticationRequest.getUserNameOrEmailAddress(),authenticationRequest.getPassword());
        Map resulet = new HashMap<>();
        if (user == null || user.getId() == null){
            return this.renderError("该用户没有注册/用户密码错误",40001);
        }else
        {
            String jwtoken = jwtUtil.createJWT(user,ttlMillis);
            resulet.put("accessToken",jwtoken);
            resulet.put("userId",user.getId());
            if (!user.getPwd().equals(MD5Util.encrypt("666666"+passwordKey)))
                resulet.put("pwdStatus",0);
            else
                resulet.put("pwdStatus",1);//1为需要强制修改密码的用户
            return this.renderSuccess(resulet);
        }
    }

}
