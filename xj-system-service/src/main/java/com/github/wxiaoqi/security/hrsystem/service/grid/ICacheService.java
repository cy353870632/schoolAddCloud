package com.github.wxiaoqi.security.hrsystem.service.grid;

import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface ICacheService {

    public void getCacheMessage(List<Map> cacheWebColumns, String cacheTotalSql, String cacheResultSql,String keyField);

    public Map cacheGridMessage(String gridKey, Integer A0188);

    public void clearCache(String gridKey, Integer A0188);


}
