package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    public UserInfoVo selectOneForVo(@Param("A0188") int A0188);
    public User selectUserByLogin(@Param("loginUsername") String loginUsername);

}