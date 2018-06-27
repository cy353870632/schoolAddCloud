package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IDeskTopDesignService extends IService<DeskTopDesign> {

    public List<String> getDeskTopDesignByuseridAndkind(int A0188, String kind);

}
