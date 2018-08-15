package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolMapper extends BaseMapper<School> {

    List<SchoolVo> selectAllSchool(@Param("keyword") String keyword, @Param("PageSize") Integer PageSize, @Param("currentPage") Integer currentPage
            , @Param("review_status") Integer review_statusd, @Param("schoolstyle_status")String schoolstyle_status);

    Integer selectScoolTotal(@Param("keyword") String keyword, @Param("review_status") Integer review_statusd, @Param("schoolstyle_status")String schoolstyle_status);

    List<SchoolVo> selectAllSchoolByUserID(@Param("keyword") String keyword, @Param("PageSize") Integer PageSize, @Param("currentPage") Integer currentPage
            , @Param("review_status") Integer review_status,@Param("userid") String userid, @Param("schoolstyle_status")String schoolstyle_status);
}