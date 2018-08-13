package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.ProvinceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProvinceMapper extends BaseMapper<Province> {

    public List<ProvinceVo> selectAllByStatus(@Param("status") Integer status);
}