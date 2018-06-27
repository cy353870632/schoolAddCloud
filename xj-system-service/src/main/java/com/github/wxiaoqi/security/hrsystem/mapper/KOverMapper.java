package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

//
public interface KOverMapper extends BaseMapper<Map> {
    public List<Map> selectByA01andBeginTmeAndEndTime(@Param("A0188")int A0188,
                                                      @Param("over_Begin")String over_Begin,
                                                      @Param("over_End")String over_End,
                                                      @Param("currpage")int currpage,
                                                      @Param("pageSize")int pageSize);
}