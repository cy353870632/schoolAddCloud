package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IKOverService {

    public List<Map> selectByA01andBeginTmeAndEndTime(int A0188, String over_Begin, String over_End,int beginNum,int pageSize);
}
