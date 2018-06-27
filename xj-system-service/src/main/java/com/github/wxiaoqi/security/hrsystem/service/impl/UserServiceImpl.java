package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebMessageMapService;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    IWebMessageMapService webMessageMapService;

    private String passwordKey = "d+#8p&nn=o30ke6%-";


    @Override
    public UserInfoVo getUserInfoVo(int A0188) {
        UserInfoVo userInfoVo = userMapper.selectOneForVo(A0188);
        userInfoVo.setImages("PublicDlg/ShowPhoto.aspx?EmployeeID="+A0188);
        List<WebMessageMap> webMessageMaps = webMessageMapService.selectByA0188(A0188);
        if (webMessageMaps.size() == 0)
            userInfoVo.setMessageCount(0);
        else
            userInfoVo.setMessageCount(webMessageMaps.size());
        //未读的消息数
        webMessageMaps = webMessageMaps.stream().filter(webMessageMap -> webMessageMap.getReadtag()==1).collect(Collectors.toList());
        if (webMessageMaps.size() == 0)
            userInfoVo.setNumNewMessage(0);
        else
        userInfoVo.setNumNewMessage(webMessageMaps.size());
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
