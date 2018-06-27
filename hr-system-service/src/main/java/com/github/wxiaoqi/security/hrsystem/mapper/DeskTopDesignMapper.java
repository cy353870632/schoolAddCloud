package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import org.apache.ibatis.annotations.Param;

public interface DeskTopDesignMapper extends BaseMapper<DeskTopDesign> {
    public DeskTopDesign getDeskTopDesignByuseridAndkind(@Param("userid") Integer A0188,@Param("kind") String kind);
}