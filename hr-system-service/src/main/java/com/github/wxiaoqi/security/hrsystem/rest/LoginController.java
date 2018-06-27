package com.github.wxiaoqi.security.hrsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.PanelOpt;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.service.IKOverService;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebColumnsService;
import com.github.wxiaoqi.security.hrsystem.service.grid.ICacheService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IHrGridCreatService;
import com.github.wxiaoqi.security.hrsystem.service.panel.IPanelService;
import com.github.wxiaoqi.security.hrsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.hrsystem.vo.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.hrsystem.vo.ResultVo;
import com.github.wxiaoqi.security.hrsystem.vo.SelfCardReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.common.Mapper;

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

    @Autowired
    IPanelService panelService;

    @Autowired
    IKOverService ikOverService;

    @Autowired
    IWebColumnsService webColumnsService;

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
    private IHrGridCreatService hrGridCreatService;

    @RequestMapping(value = "Authenticate", method = RequestMethod.POST)
    public ResultVo<Object> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletRequest request) throws Exception {
        log.info(authenticationRequest.getUserNameOrEmailAddress()+" require logging...");
        User user = userService.getUserInfo(authenticationRequest.getUserNameOrEmailAddress(),authenticationRequest.getPassword());
        Map resulet = new HashMap<>();
        if (user == null || user.getA0188() == null){
            resulet.put("message","该用户没有注册/用户密码错误");
            return new ResultVo(null,false,resulet);
        }else
        {
            String jwtoken = jwtUtil.createJWT(user,ttlMillis);
            resulet.put("accessToken",jwtoken);
            resulet.put("userId",user.getA0188());
            return new ResultVo(resulet,true,null);
        }
    }

}
