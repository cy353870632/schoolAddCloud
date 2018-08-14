package com.github.wxiaoqi.security.xjsystem.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * @author chengyuan
 * @create 2018-05-24 16:53
 **/
public class UserMessage {

    @Autowired
    JWTUtil jwtUtil;


    public static String getUserId() throws Exception{
        String token =getHttpServletRequest().getHeader("Authorization");
        Claims claims = new JWTUtil().parseJWT(token);
        return claims.get("id", String.class);
    }
    public static String getUserCode() throws Exception{
        String token =getHttpServletRequest().getHeader("Authorization");
        Claims claims = new JWTUtil().parseJWT(token);
        return claims.get("user_code", String.class);
    }
    public static String getUserRole() throws Exception{
        String token =getHttpServletRequest().getHeader("Authorization");
        Claims claims = new JWTUtil().parseJWT(token);
        return claims.get("user_role", String.class);
    }

    public static HttpServletRequest getHttpServletRequest(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes())
                .getRequest();
        return request;
    }

}
