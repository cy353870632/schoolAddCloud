package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UtilMapper extends BaseMapper<Map>{
    public List<Map> selectBysql(@Param("sql") String sql);
    public List<CaseInsensitiveMap> selectBysqlForCaseInsensitiveMap(@Param("sql") String sql);
    public List<Map> selectByTableName(@Param("tablename") String tablename);
}