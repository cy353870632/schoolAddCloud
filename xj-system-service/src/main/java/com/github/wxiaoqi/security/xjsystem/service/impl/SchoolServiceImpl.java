package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.mapper.MenuMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.SchoolMapper;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.MenuUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    IProviceService proviceService;

    @Autowired
    ICityService cityService;

    @Autowired
    IAreaService areaService;

    @Autowired
    ISysDicService sysDicService;

    @Override
    public List getAllSchool(String keyword, Integer pageSize, Integer currentPage,int review_status,String user_code, String id,String schoolstyle_status) {

//        Map cacheMap = cacheService.cacheGridMessage("getAllmenu",pageSize+currentPage);
//        List<JSONObject> menuObejct = (List<JSONObject>)cacheMap.get("cacheListMap");
//        if (menuObejct == null){
            currentPage = (currentPage-1)*pageSize;
        List<SchoolVo> schoolVoList = new ArrayList<>();
        List<System_dic> system_dics = sysDicService.getChildByDicName("SCHOOLSTYLE");
        if (user_code.equals("999") || user_code.equals("998")){
            schoolVoList = schoolMapper.selectAllSchool(keyword,pageSize,currentPage,review_status,schoolstyle_status);
            for (SchoolVo schoolVo:schoolVoList){
                String style = "";
                String[] s = schoolVo.getStyle_name().split(",");
                for (String s1:s){
                    style = style + system_dics.stream().filter(f->f.getDic_code().equals(s1)).collect(Collectors.toList()).get(0).getDic_name_c()+",";
                }
                schoolVo.setStyle_name(style.substring(0,style.length()-1));
            }
        }
        else {
            schoolVoList = schoolMapper.selectAllSchoolByUserID(keyword, pageSize, currentPage, review_status, id,schoolstyle_status);
            for (SchoolVo schoolVo:schoolVoList){
                String style = "";
                String[] s = schoolVo.getStyle_name().split(",");
                for (String s1:s){
                    style = style + system_dics.stream().filter(f->f.getDic_code().equals(s1)).collect(Collectors.toList()).get(0).getDic_name_c()+",";
                }
                schoolVo.setStyle_name(style.substring(0,style.length()-1));
            }
        }
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
//            cacheService.getCacheMessage(menuList);
//            cacheService.cacheGridMessage("getAllmenuParent","Parent");
            return schoolVoList;
//        }
//        return menuObejct;
    }

    @Override
    public Integer getSchoolTotal(String keyword,int review_status,String schoolstyle_status) {
        return schoolMapper.selectScoolTotal(keyword,review_status,schoolstyle_status);
    }

    @Override
    public Integer addSchool(School school) {
        school.setStatus("1");
        school.setCreat_date(new Date());
        school.setUpdate_date(new Date());
        school.setRead_only("0");
        school.setId(UUID.randomUUID().toString());
        school.setReview_status(2);
        if (school.getLongitude()==0)
            school.setLongitude(0);
        if (school.getLatitude()==0)
            school.setLatitude(0);
        school.setReview_user("0");
        return schoolMapper.insert(school);
    }

//    @Override
//    public Integer getMenuTotal(String keyword) {
//        Integer total = menuMapper.selectMenuTotal(keyword);
//        return total;
//    }


}
