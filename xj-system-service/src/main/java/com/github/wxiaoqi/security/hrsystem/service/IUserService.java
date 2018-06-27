package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.constant.UserConstant;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IUserService extends IService<User> {

    public UserInfoVo getUserInfoVo(int A0188);

    public User getUserInfo(String loginUserName,String password);

}
