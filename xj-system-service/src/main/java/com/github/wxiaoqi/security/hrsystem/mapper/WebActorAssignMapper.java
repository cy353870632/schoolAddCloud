package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.WebActorAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebActorAssignMapper extends BaseMapper<WebActorAssign> {

    public List<Integer> selectAcordidByA0188(@Param("A0188") Integer A0188);

}