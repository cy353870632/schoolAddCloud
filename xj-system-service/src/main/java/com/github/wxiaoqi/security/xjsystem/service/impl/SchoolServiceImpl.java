package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.SchoolMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.ISchoolService;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
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
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper,School> implements ISchoolService{


    @Autowired
    MenuMapper menuMapper;

    @Autowired
    SchoolMapper schoolMapper;

    private String passwordKey = "d+#8p&nn=o30ke6%-";


    @Autowired
    ICacheService cacheService;

    @Override
    public List getAllSchool(String keyword, Integer pageSize, Integer currentPage) {

//        Map cacheMap = cacheService.cacheGridMessage("getAllmenu",pageSize+currentPage);
//        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
//        if (menuObejct == null){
            currentPage = (currentPage-1)*pageSize;
            List<SchoolVo> schoolVoList = schoolMapper.selectAllSchool(keyword,pageSize,currentPage);
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
//            cacheService.getCacheMessage(menuList);
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
            return schoolVoList;
//        }
//        return menuObejct;
    }

    @Override
    public Integer getSchoolTotal(String keyword) {
        return schoolMapper.selectScoolTotal(keyword);
    }

//    @Override
//    public Integer getMenuTotal(String keyword) {
//        Integer total = menuMapper.selectMenuTotal(keyword);
//        return total;
//    }


}
