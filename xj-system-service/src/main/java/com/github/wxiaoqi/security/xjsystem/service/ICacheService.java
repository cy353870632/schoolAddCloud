package com.github.wxiaoqi.security.xjsystem.service;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface ICacheService {

    public void getCacheMessage(List cacheListMap);

    public Map cacheGridMessage(String cacheName,String par1);

    public void clearCache(String cacheName,String par1);


}
