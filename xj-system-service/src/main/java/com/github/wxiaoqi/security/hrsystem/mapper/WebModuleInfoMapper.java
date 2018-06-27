package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.entity.WebModuleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WebModuleInfoMapper extends BaseMapper<WebModuleInfo> {

    public List<WebModuleInfo> selectModeuleBymodeid(@Param("modeId") List<String> modeId);
    public List<WebModuleInfo> selectAllBymodeid();

}