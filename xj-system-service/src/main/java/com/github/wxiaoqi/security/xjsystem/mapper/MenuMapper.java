package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.sun.javaws.jnl.LibraryDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectByRole(@Param("roles") List roles);

    List<Menu> selectAllMenu(@Param("keyword") String keyword,@Param("PageSize") Integer PageSize,@Param("currentPage") Integer currentPage);

    Integer selectMenuTotal(@Param("keyword") String keyword);

    List<Menu> selectAllParent();

}