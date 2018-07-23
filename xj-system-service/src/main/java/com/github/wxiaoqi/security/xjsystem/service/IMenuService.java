package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IMenuService extends IService<Menu> {

    public List getMenu(String role);

    public Boolean checkMenu(String user_role,String MenuCodePath);

    public List getAllMenu(String keyword, Integer pageSize, Integer currentPage);

    public Integer getMenuTotal(String keyword);

}
