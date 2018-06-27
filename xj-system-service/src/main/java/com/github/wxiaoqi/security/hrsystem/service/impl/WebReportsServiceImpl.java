package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.entity.WebReports;
import com.github.wxiaoqi.security.hrsystem.mapper.RyQuerySuitesMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebReportsMapper;
import com.github.wxiaoqi.security.hrsystem.service.IDeskTopDesignService;
import com.github.wxiaoqi.security.hrsystem.service.IRyQuerySuitesService;
import com.github.wxiaoqi.security.hrsystem.service.IWebReportsService;
import com.github.wxiaoqi.security.hrsystem.vo.RyQuerySuitesVo;
import com.github.wxiaoqi.security.hrsystem.vo.WebReportsVo;
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
public class WebReportsServiceImpl extends ServiceImpl<WebReportsMapper,WebReports> implements IWebReportsService{


    @Autowired
    UserMapper userMapper;

    @Autowired
    WebReportsMapper webReportsMapper;

    @Autowired
    IDeskTopDesignService deskTopDesignService;


    @Override
    public List<Map> getWebReportsBystrDetail(int A0188) {

        List<String> deskTopDesignlist = deskTopDesignService.getDeskTopDesignByuseridAndkind(A0188,"1");
        if (deskTopDesignlist == null || deskTopDesignlist.size()<1){
            return null;
        }else
        {
            List<WebReportsVo> webReportsVoList = webReportsMapper.getRWebReportsBystrDetail(deskTopDesignlist);
            if (webReportsVoList == null || webReportsVoList.size()<1)
                return null;
            else
            {
                List result = new ArrayList<>();
                for (WebReportsVo webReportsVo : webReportsVoList){
                    Map map = new HashMap<>();
                    map.put("id",webReportsVo.getRpt_id());
                    map.put("name",webReportsVo.getRpt_name());
                    map.put("tag",1);
                    result.add(map);
                }
                return result;
            }
        }
    }
}
