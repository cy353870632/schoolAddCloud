package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

import java.util.*;

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
        String password1 = MD5Util.encrypt("666666"+passwordKey);
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
        String user_image = claims.get("user_image", String.class);
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
        userInfoVo.setImages(user_image);
        return userInfoVo;
    }

    @Override
    public List<User> getPromoter(String selfid,String keyword, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<User> promoterList = userMapper.selectPromoter(selfid,keyword,pageSize,currentPage,"1");
        return promoterList;
    }

    @Override
    public Integer getPromoterTotal(String selfid, String keyword) {
        return userMapper.selectPromoterTotal(selfid,keyword,"1");
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
        user.setPwd( MD5Util.encrypt("666666"+passwordKey));
        user.setId(UUID.randomUUID().toString());
        user.setDelete_status("1");
        return userMapper.insert(user);
    }

    @Override
    public Integer deletePromoter(String uid,String roleCode){
        User user = this.selectById(uid);
        if (user==null){
            return 3;//不存在
        }
        if (roleCode.equals("999")){//真删除
            userMapper.deleteById(uid);
        }else//假删除
        {
            if (user.getRead_only().equals("0")){
                user.setDelete_status("0");
                user.setUpdate_date(new Date());
                EntityWrapper<User> wrapper = new EntityWrapper<User>();
                wrapper.eq("id",uid);
                userMapper.update(user,wrapper);
            }else
            {
                return 2;//只读数据
            }
        }
        return 1;
    }

    @Override
    public Integer updatePromoter(User user,String roleCode) {
        User user1 = userMapper.selectById(user.getId());
        if (user1==null){
            return 3;
        }
        user1.setU_name(user.getU_name());
        user1.setPhone(user.getPhone());
        user1.setSex(user.getSex());
        user1.setUpdate_date(new Date());
        EntityWrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.eq("id",user1.getId());
        if (user1.getRead_only().equals("0")){
            userMapper.update(user1,wrapper);
        }else
        {
            if (roleCode.equals("999")){
                userMapper.update(user1,wrapper);
            }else
            {
                return 2;
            }
        }
        return 1;
    }

    @Override
    public Integer restPwd(String id,String user_code) {
        User user = userMapper.selectById(id);
        if (user==null){
            return 3;
        }
        user.setPwd( MD5Util.encrypt("666666"+passwordKey));
        EntityWrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.eq("id",id);
        if (user.getRead_only().equals("0")){
            userMapper.update(user,wrapper);
        }else
        {
            if (user_code.equals("999")){
                userMapper.update(user,wrapper);
            }else {
                return 2;
            }
        }
        return 1;
    }

    @Override
    public List<User> getManageUser(String selfid, String keyword, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<User> promoterList = userMapper.selectPromoter(selfid,keyword,pageSize,currentPage,"998");
        return promoterList;
    }

    @Override
    public Integer getManageUserTotal(String selfid, String keyword) {
        return userMapper.selectPromoterTotal(selfid,keyword,"998");

    }

    @Override
    public Integer addManageUser(User user) {
        Role role = roleService.getRoleByRname(RoleEnum.SYSADMIN.toString());
        user.setPwd(MD5Util.encrypt("666666"));
        user.setCreat_date(new Date());
        user.setUpdate_date(new Date());
        user.setUser_code("998");
        user.setRead_only("0");
        user.setRole(role.getId());
        user.setPwd( MD5Util.encrypt("666666"+passwordKey));
        user.setId(UUID.randomUUID().toString());
        user.setDelete_status("1");
        return userMapper.insert(user);
    }

    @Override
    public Integer changPwd(String id, String oldPassword, String password) {
        User user = userMapper.selectById(id);
        if (user.getPwd().equals(MD5Util.encrypt(oldPassword+passwordKey))){
            user.setPwd( MD5Util.encrypt(password+passwordKey));
            EntityWrapper<User> wrapper = new EntityWrapper<User>();
            wrapper.eq("id",id);
            return userMapper.update(user,wrapper);
        }else
            return 3;
    }

    @Override
    public Integer deleteManageUser(String uid, String roleCode) {
        User user = this.selectById(uid);
        if (user==null){
            return 3;//不存在
        }
        return userMapper.deleteById(uid);
    }

    @Override
    public Integer updateManageUser(User user, String roleCode) {
        User user1 = userMapper.selectById(user.getId());
        if (user1==null){
            return 3;
        }
        user1.setU_name(user.getU_name());
        user1.setPhone(user.getPhone());
        user1.setSex(user.getSex());
        user1.setUpdate_date(new Date());
        EntityWrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.eq("id",user1.getId());
        return userMapper.update(user1,wrapper);
    }

    @Override
    public Integer restManageUserPwd(String id, String user_code) {
        User user = userMapper.selectById(id);
        if (user==null){
            return 3;
        }
        user.setPwd( MD5Util.encrypt("666666"+passwordKey));
        EntityWrapper<User> wrapper = new EntityWrapper<User>();
        wrapper.eq("id",id);
        return userMapper.update(user,wrapper);
    }


}
