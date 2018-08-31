package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.entity.System_doc;
import com.github.wxiaoqi.security.xjsystem.vo.SysDicVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysDocMapper extends BaseMapper<System_doc> {

    public List<System_doc> getAllSysDoc(@Param("keyword") String keyword, @Param("startDoTime")Date startDoTime, @Param("endDoTime")Date endDoTime, @Param("pageSize") Integer pageSize, @Param("currentPage") Integer currentPage);

    public Integer getAllSysDocTotal(@Param("keyword") String keyword,@Param("startDoTime")Date startDoTime,@Param("endDoTime")Date endDoTime);

}