package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public UserInfoVo getUserInfoVo(int A0188) {
        UserInfoVo userInfoVo = userMapper.selectOneForVo(A0188);
        userInfoVo.setImages("PublicDlg/ShowPhoto.aspx?EmployeeID="+A0188);
            userInfoVo.setMessageCount(0);
        //未读的消息数
        return userInfoVo;
    }

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
}
