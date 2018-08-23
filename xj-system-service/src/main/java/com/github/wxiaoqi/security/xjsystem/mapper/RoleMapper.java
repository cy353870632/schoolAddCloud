package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    public String getRoleNameByidTop(@Param("idList") List<String> idList);

    public Role getRoleByRname(@Param("r_name") String r_name);

    public List<Role> selectAllRole(@Param("keyword") String keyword,@Param("currentPage") Integer currentPage,@Param("PageSize") Integer PageSize);

    public Integer selectAllRoletotal(@Param("keyword") String keyword);


}