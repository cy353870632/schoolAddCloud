package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 字段查询类
 **/
@Service
@Slf4j
public class CacheServiceImpl implements ICacheService {

    public List cacheListMap;


    @Override
    public void getCacheMessage(List cacheListMap) {
        this.cacheListMap = cacheListMap;
    }

    @Override
    @Cache(key = "u{1}:u{2}")
    public Map cacheGridMessage(String cacheName,String par1){
        Map resultMap = new HashMap<>();
        resultMap.put("cacheListMap",cacheListMap);
        return resultMap;
    }

    @Override
    @CacheClear(key = "u{1}:u{2}")
    public void clearCache(String cacheName,String par1) {
        log.info("清除了缓存 "+cacheName+"---"+par1);
    }

}
