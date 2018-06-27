package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.vo.RyQuerySuitesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RyQuerySuitesMapper extends BaseMapper<RyQuerySuites> {
    public List<RyQuerySuitesVo> getRyQuerySuitesBystrDetail(@Param("strDetail") List strDetail);
}