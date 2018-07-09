package com.github.wxiaoqi.security.xjsystem.vo;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;



    private String userNameOrEmailAddress;
    private String password;


    public JwtAuthenticationRequest(String username, String password) {
        this.userNameOrEmailAddress = username;
        this.password = password;
    }

    public JwtAuthenticationRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserNameOrEmailAddress() {
        return userNameOrEmailAddress;
    }

    public void setUserNameOrEmailAddress(String userNameOrEmailAddress) {
        this.userNameOrEmailAddress = userNameOrEmailAddress;
    }

}
