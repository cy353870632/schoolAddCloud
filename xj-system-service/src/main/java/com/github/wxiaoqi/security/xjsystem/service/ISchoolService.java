package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface ISchoolService extends IService<School> {

    public List<SchoolVo> getAllSchool(String keyword, Integer pageSize, Integer currentPage,int review_status,String user_code, String id,String schoolstyle_status);

    public Integer getSchoolTotal(String keyword,int review_status,String schoolstyle_status);
//
    public Integer addSchool(School school);
//
//    public Integer upMenu(Menu menu, Integer status);


}
