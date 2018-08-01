package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.druid.support.logging.Log;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Override
    @CacheClear(key = "dicParent:u{1}")
    public void cacheClear(String parent_id) {
        log.info("清除了字典缓存");
    }

    @Override
    public Integer addSysDic(System_dic system_dic) {
        system_dic.setStatus("1");
        system_dic.setId(UUID.randomUUID().toString());
        system_dic.setCreat_date(new Date());
        system_dic.setUpdate_date(new Date());
        system_dic.setRead_only("0");
        system_dic.setEnd_mark("1");
        return sysDicMapper.insert(system_dic);
    }

    @Override
    public List getAllParent() {
        EntityWrapper<System_dic> wrapper = new EntityWrapper<System_dic>();
        wrapper.eq("end_mark","0");
        wrapper.eq("status","1");
        wrapper.orderBy("seq",true);
        return sysDicMapper.selectList(wrapper);
    }
}
