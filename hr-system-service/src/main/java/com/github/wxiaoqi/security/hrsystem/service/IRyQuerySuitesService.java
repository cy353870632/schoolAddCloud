package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-30 16:23
 */

public interface IRyQuerySuitesService extends IService<RyQuerySuites> {

    public List<Map> getRyQuerySuitesBystrDetail(int A0188);

}
