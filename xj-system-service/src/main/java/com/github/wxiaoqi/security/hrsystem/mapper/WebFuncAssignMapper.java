package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebFuncAssignMapper extends BaseMapper<WebFuncAssign> {

    public List<String> selectFunccodeByActorid(@Param("actoridlist") List<Integer> actoridlist);

}