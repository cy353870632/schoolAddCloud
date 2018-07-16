package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IUserService extends IService<User> {

    public User getUserInfo(String loginUserName,String password);

    public UserInfoVo getInfo(Claims claims);

    public List<User> getPromoter(String selfid,String keyword, Integer pageSize, Integer currentPage);

    public Integer getPromoterTotal(String selfid,String keyword);

    public Integer addPromoter(User user);


}
