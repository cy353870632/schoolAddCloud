package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolMapper extends BaseMapper<School> {

    List<SchoolVo> selectAllSchool(@Param("keyword") String keyword, @Param("PageSize") Integer PageSize, @Param("currentPage") Integer currentPage);

    Integer selectScoolTotal(@Param("keyword") String keyword);

}