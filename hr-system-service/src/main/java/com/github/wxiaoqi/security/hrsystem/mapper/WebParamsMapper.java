package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.WebModuleInfo;
import com.github.wxiaoqi.security.hrsystem.entity.WebParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WebParamsMapper extends BaseMapper<WebParams> {

    public List<WebParams> selectWebParamsByparamName(@Param("paramname") List<String> paramname);

    public Map selectAllClowms(String paramname);


}