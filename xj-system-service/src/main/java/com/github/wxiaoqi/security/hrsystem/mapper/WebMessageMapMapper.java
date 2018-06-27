package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

public interface WebMessageMapMapper extends BaseMapper<WebMessageMap> {

    public List<WebMessageMap> selectByA0188(@Param("A0188") int A0188);

}