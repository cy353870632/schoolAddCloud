package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDicMapper extends BaseMapper<System_dic> {

    public List<System_dic> getAllParent(@Param("keyword")String keyword, @Param("pageSize")Integer pageSize,@Param("currentPage") Integer currentPage);

    public Integer getAllParentTotal(@Param("keyword")String keyword);

}