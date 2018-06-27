package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;

import java.util.List;
import java.util.Map;

public interface WebColumnsMapper extends BaseMapper<WebColumns> {
    public List<WebColumns> selectByTablename(@Param("tablename") String tablename);

    public List<PanelVo> selectPanelVoByTablename(@Param("tablename") String tablename);

    public List<WebColumns> selctBySqlToWebColms(@Param("sql") String sql);

    public List<Map> selctBySqlToMap(@Param("sql") String sql);

    public Boolean updateBySql(@Param("sql") String sql);
    public Boolean insertBySql(@Param("sql") String sql);

}