package com.github.wxiaoqi.security.auth.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by Ace on 2017/6/11.
 */
@Data
public class ResultVo<T> {

    T result;
    boolean success;
    T error;
    String targetUrl;
    boolean _abp = true;
    boolean unAuthorizedRequest = false;

    public ResultVo(T var,boolean success,T error){
        this.result = var;
        this.success = success;
        this.error = error;
    }
}
