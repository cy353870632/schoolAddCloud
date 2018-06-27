package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.mapper.KOverMapper;
import com.github.wxiaoqi.security.hrsystem.service.IKOverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 字段查询类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class KOverServiceImpl extends ServiceImpl<KOverMapper,Map> implements IKOverService{


    @Autowired
    KOverMapper kOverMapper;


    @Override
    public List<Map> selectByA01andBeginTmeAndEndTime(int A0188, String over_Begin, String over_End,int beginNum,int pageSize) {
        over_Begin = over_Begin+" 00:00:00";
        over_End = over_End+" 23:59:59";
        List<Map> result = kOverMapper.selectByA01andBeginTmeAndEndTime(A0188,over_Begin,over_End,beginNum,pageSize);
        List<Map> returnList = new ArrayList<>();
        for (Map map:result){
            Map mapresult = new HashMap<>();
            Iterator<String> iter = map.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                mapresult.put(("k_over"+"_"+key).toUpperCase(),map.get(key));
            }
            returnList.add(mapresult);
        }
        return returnList;
    }

}
