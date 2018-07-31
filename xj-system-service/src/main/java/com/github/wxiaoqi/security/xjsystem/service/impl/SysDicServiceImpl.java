package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.SysDicMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.ISysDicService;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.SysDicVo;
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
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper,System_dic> implements ISysDicService{


    @Autowired
    SysDicMapper sysDicMapper;


    private String passwordKey = "d+#8p&nn=o30ke6%-";


    @Autowired
    ICacheService cacheService;

    @Override
    public List getAllSysDic(String keyword, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<SysDicVo> sysDicVos = sysDicMapper.getAllParent(keyword,pageSize,currentPage);
        return sysDicVos;
    }

    @Override
    public Integer getSysDicTotal(String keyword) {
        return sysDicMapper.getAllParentTotal(keyword);
    }

    @Override
    @Cache(key = "dicParent:u{1}")
    public List<System_dic> getChildByParentid(String parent_id) {
        return sysDicMapper.getchildByParentId(parent_id);
    }
}
