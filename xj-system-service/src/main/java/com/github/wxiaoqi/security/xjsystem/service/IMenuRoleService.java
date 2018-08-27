package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.entity.MenuRole;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IMenuRoleService extends IService<MenuRole> {

    List<String> getMenuIdByRoleId(String roleId);

}
