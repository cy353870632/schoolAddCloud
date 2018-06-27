package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebFuncFrameService extends IService<WebFuncFrame> {

    public List<WebFuncFrame> selectByAcotrid(Integer A0188);

    public List<DefaultVo> selectBymodid(Integer A0188, String modeid);


}
