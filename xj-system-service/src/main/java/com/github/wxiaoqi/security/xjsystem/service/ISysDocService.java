package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.entity.System_doc;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface ISysDocService extends IService<System_doc> {

    public List getAllSysDoc(String keyword, Date startDoTime, Date endDoTime, Integer pageSize, Integer currentPage);

    public Integer getSysDocTotal(String keyword,Date startDoTime,Date endDoTime);

    public void addSysDoc(String active,String level);


}
