package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.DeskTopDesign;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.mapper.DeskTopDesignMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.RyQuerySuitesMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.service.IDeskTopDesignService;
import com.github.wxiaoqi.security.hrsystem.service.IRyQuerySuitesService;
import com.github.wxiaoqi.security.hrsystem.vo.RyQuerySuitesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 用户服务实现类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RyQuerySuitesServiceImpl extends ServiceImpl<RyQuerySuitesMapper,RyQuerySuites> implements IRyQuerySuitesService{


    @Autowired
    UserMapper userMapper;

    @Autowired
    RyQuerySuitesMapper ryQuerySuitesMapper;

    @Autowired
    IDeskTopDesignService deskTopDesignService;


    @Override
    public List<Map> getRyQuerySuitesBystrDetail(int A0188) {

        List<String> deskTopDesignlist = deskTopDesignService.getDeskTopDesignByuseridAndkind(A0188,"2");
        if (deskTopDesignlist == null || deskTopDesignlist.size()<1){
            return null;
        }else
        {
            List<RyQuerySuitesVo> ryQuerySuitesVoList = ryQuerySuitesMapper.getRyQuerySuitesBystrDetail(deskTopDesignlist);
            if (ryQuerySuitesVoList == null || ryQuerySuitesVoList.size()<1)
                return null;
            else
            {
                List result = new ArrayList<>();
                for (RyQuerySuitesVo ryQuerySuitesVo : ryQuerySuitesVoList){
                    Map map = new HashMap<>();
                    map.put("id",ryQuerySuitesVo.getSuite_id());
                    map.put("name",ryQuerySuitesVo.getCaption());
                    map.put("tag",ryQuerySuitesVo.getModule_id());
                    result.add(map);
                }
                return result;
            }
        }
    }
}
