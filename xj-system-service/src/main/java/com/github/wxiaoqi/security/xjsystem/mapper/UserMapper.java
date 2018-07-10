package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    public UserInfoVo selectOneForVo(@Param("id") String id);
    public User selectUserByLogin(@Param("loginUsername") String loginUsername);

}