package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.mapper.DeskTopDesignMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.service.IDeskTopDesignService;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebMessageMapService;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class DeskTopDesignServiceImpl extends ServiceImpl<DeskTopDesignMapper,DeskTopDesign> implements IDeskTopDesignService{


    @Autowired
    UserMapper userMapper;

    @Autowired
    DeskTopDesignMapper deskTopDesignMapper;


    @Override
    public List<String> getDeskTopDesignByuseridAndkind(int A0188, String kind) {
        DeskTopDesign deskTopDesign = deskTopDesignMapper.getDeskTopDesignByuseridAndkind(A0188,kind);
        if (deskTopDesign == null || deskTopDesign.getDetail().length()<3){
            return null;
        }else
        {
            String str = deskTopDesign.getDetail();
            str = str.substring(1,str.length()-1);
            String[] strList = str.split("\\$");
            List<String> result = new ArrayList<>();
            for (String str1 : strList){
                result.add(str1);
            }
            return result;
        }

    }
}
