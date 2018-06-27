package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebMessageMapService extends IService<WebMessageMap> {

    public List<WebMessageMap> selectByA0188(int A0188);

}
