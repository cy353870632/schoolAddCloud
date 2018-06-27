package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.entity.WebModuleInfo;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebModuleInfoService extends IService<WebModuleInfo> {

    public List selectModeAndFramByA0188(Integer A0188);

}
