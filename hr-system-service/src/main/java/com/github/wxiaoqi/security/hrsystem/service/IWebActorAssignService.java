package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.WebActorAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebActorAssignService extends IService<WebActorAssign> {

    public List<Integer> selectActoridByA0188(Integer A0188);
}
