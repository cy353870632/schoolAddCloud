package com.github.wxiaoqi.security.auth.controller;

import com.github.wxiaoqi.security.auth.service.AuthService;
import com.github.wxiaoqi.security.auth.util.user.IpHelperUtils;
import com.github.wxiaoqi.security.auth.util.user.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.auth.bean.ResultVo;
import com.thoughtworks.xstream.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("jwt")
@Slf4j
@CrossOrigin("*")
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Value("${jwt.expire}")
    private int expire;//token过期时间

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "token", method = RequestMethod.POST)
    public ResultVo<Object> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,HttpServletRequest request) throws Exception {
        log.info(authenticationRequest.getUserNameOrEmailAddress()+" require logging...");
        //获取请求ip
        String ipaddress = IpHelperUtils.getIpAddr(request);
        try {
            Map map = (Map) authService.login(authenticationRequest);
            map.put("expireInSeconds",expire);
            return new ResultVo(map,true,null);
        }catch (Exception e){
            Map map = new HashMap<>();
            map.put("code",0);
            map.put("message","Login failed!");
            map.put("details","Invalid user name or password");
            return new ResultVo(null,false,map);
        }
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ObjectRestResponse<String> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws Exception {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        return new ObjectRestResponse<>().data(refreshedToken);
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    public ObjectRestResponse<?> verify(String token) throws Exception {
        authService.validate(token);
        return new ObjectRestResponse<>();
    }
}
