package com.github.wxiaoqi.security.hrsystem.service.grid.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.mapper.WebColumnsMapper;
import com.github.wxiaoqi.security.hrsystem.service.grid.ICacheService;
import com.github.wxiaoqi.security.hrsystem.vo.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 字段查询类
 **/
@Service
@Slf4j
public class CacheServiceImpl implements ICacheService {

    public List<Map> cacheWebColumns;
    public String cacheTotalSql;
    public String cacheResultSql;
    public String keyField;

    @Override
    public void getCacheMessage(List<Map> cacheWebColumns, String cacheTotalSql, String cacheResultSql, String keyField) {
        this.cacheWebColumns = cacheWebColumns;
        this.cacheTotalSql = cacheTotalSql;
        this.cacheResultSql = cacheResultSql;
        this.keyField = keyField;
    }

    @Override
    @Cache(key = "u{1}:u{2}")
    public Map cacheGridMessage(String gridKey,Integer A0188){
        Map resultMap = new HashMap<>();
        resultMap.put("fields",cacheWebColumns);
        resultMap.put("totalSql",cacheTotalSql);
        resultMap.put("resultSql",cacheResultSql);
        resultMap.put("keyField",keyField);
        return resultMap;
    }

    @Override
    @CacheClear(key = "u{1}:u{2}")
    public void clearCache(String gridKey, Integer A0188) {
        log.info("清除了缓存 "+gridKey);
    }

}
