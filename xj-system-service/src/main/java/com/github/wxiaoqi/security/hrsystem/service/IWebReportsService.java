package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.RyQuerySuites;
import com.github.wxiaoqi.security.hrsystem.entity.WebReports;
import com.github.wxiaoqi.security.hrsystem.vo.WebReportsVo;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-30 16:23
 */

public interface IWebReportsService extends IService<WebReports> {

    public List<Map> getWebReportsBystrDetail(int A0188);

}
