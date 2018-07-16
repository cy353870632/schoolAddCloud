package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IRoleService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.sysEnum.RoleEnum;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl  extends ServiceImpl<UserMapper,User> implements IUserService{
    @Autowired
    UserMapper userMapper;

    private String passwordKey = "d+#8p&nn=o30ke6%-";

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ICacheService cacheService;

    @Autowired
    IRoleService roleService;

    @Override
    public User getUserInfo(String loginUserName,String password)
    {
        password = MD5Util.encrypt(password+passwordKey);
        User user = userMapper.selectUserByLogin(loginUserName);
        if (StringUtils.equalsIgnoreCase(password,user.getPwd())){
            return user;
        }
        return null;
    }

    @Override
    public UserInfoVo getInfo(Claims claims) {
        String user_role = claims.get("user_role", String.class);
        String user_name = claims.get("user_name", String.class);
        List<String> roleList = new ArrayList<>();
        if (user_role.contains(","))
        {
            for (String r : user_role.split(",")){
                roleList.add(r);
            }
        }else
            roleList.add(user_role);
        String role_name = roleMapper.getRoleNameByidTop(roleList);

        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setName(user_name);
        userInfoVo.setRole(role_name);
        userInfoVo.setImages("");
        return userInfoVo;
    }

    @Override
    public List<User> getPromoter(String selfid,String keyword, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<User> promoterList = userMapper.selectPromoter(selfid,keyword,pageSize,currentPage);
        return promoterList;
    }

    @Override
    public Integer getPromoterTotal(String selfid, String keyword) {
        return userMapper.selectPromoterTotal(selfid,keyword);
    }

    @Override
    public Integer addPromoter(User user) {
        Role role = roleService.getRoleByRname(RoleEnum.PROMOTER.toString());
        user.setPwd(MD5Util.encrypt("666666"));
        user.setCreat_date(new Date());
        user.setUpdate_date(new Date());
        user.setUser_code("1");
        user.setRead_only("0");
        user.setRole(role.getId());
        return userMapper.insert(user);

    }
}
