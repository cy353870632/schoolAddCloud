package com.github.wxiaoqi.security.auth.service;


import com.github.wxiaoqi.security.auth.util.user.JwtAuthenticationRequest;

public interface AuthService {
    Object login(JwtAuthenticationRequest authenticationRequest) throws Exception;
    String refresh(String oldToken) throws Exception;
    void validate(String token) throws Exception;
}
