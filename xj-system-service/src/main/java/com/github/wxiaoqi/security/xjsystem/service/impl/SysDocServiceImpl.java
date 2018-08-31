package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.entity.System_doc;
import com.github.wxiaoqi.security.xjsystem.mapper.SysDicMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.SysDocMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.ISysDicService;
import com.github.wxiaoqi.security.xjsystem.service.ISysDocService;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.SysDicVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysDocServiceImpl extends ServiceImpl<SysDocMapper,System_doc> implements ISysDocService{


    @Autowired
    ICacheService cacheService;
    @Autowired
    SysDocMapper sysDocMapper;

    @Override
    public List getAllSysDoc(String keyword,Date startDoTime,Date endDoTime, Integer pageSize, Integer currentPage) {
        currentPage = (currentPage-1)*pageSize;
        List<System_doc> sysDicVos = sysDocMapper.getAllSysDoc(keyword,startDoTime,endDoTime,pageSize,currentPage);
        return sysDicVos;
    }
    @Override
    public Integer getSysDocTotal(String keyword,Date startDoTime,Date endDoTime) {
        return sysDocMapper.getAllSysDocTotal(keyword,startDoTime,endDoTime);
    }

    @Override
    public void addSysDoc( String active, String level) {
        try {
            String user_id = UserMessage.getUserId();
            String user_name = UserMessage.getUserName();
            System_doc system_doc = new System_doc();
            system_doc.setId(UUID.randomUUID().toString());
            system_doc.setDo_time(new Date());
            system_doc.setUser_id(user_id);
            system_doc.setUser_name(user_name);
            system_doc.setActive(active);
            system_doc.setLevel(level);
            sysDocMapper.insert(system_doc);
        } catch (Exception e) {

        }
    }

}
