package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UtilMapper extends BaseMapper<Map>{
    public List<Map> selectBysql(@Param("sql") String sql);
    public List<CaseInsensitiveMap> selectBysqlForCaseInsensitiveMap(@Param("sql") String sql);
    public List<Map> selectByTableName(@Param("tablename") String tablename);
}