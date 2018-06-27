package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebFuncAssignService extends IService<WebFuncAssign> {

    public List<String> selectFunccodeByAcotrid(Integer A0188);

}
