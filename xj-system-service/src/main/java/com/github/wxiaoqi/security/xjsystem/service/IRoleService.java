package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IRoleService extends IService<Role> {

    public Role getRoleByRname(String r_name);

    public List<Role> getAllRole(String keyword, Integer pageSize, Integer currentPage);

    public Integer getAllRoleTotal(String keyword);

    public Integer addRole(Role role);

    public Integer upRole(Role role,Role oldrole);

    public Boolean upRoleMenu(Role role,List<String> menuId);

    public Boolean checkRoleStatus(String roleId);

}
