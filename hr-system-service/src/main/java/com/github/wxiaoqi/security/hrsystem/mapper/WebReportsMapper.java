package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.entity.WebReports;
import com.github.wxiaoqi.security.hrsystem.vo.RyQuerySuitesVo;
import com.github.wxiaoqi.security.hrsystem.vo.WebReportsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebReportsMapper extends BaseMapper<WebReports> {
    public List<WebReportsVo> getRWebReportsBystrDetail(@Param("strDetail") List strDetail);
}