package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface ISysDicService extends IService<System_dic> {

    public List getAllSysDic(String keyword, Integer pageSize, Integer currentPage);

    public Integer getSysDicTotal(String keyword);

    public List getChildByParentid(String parent_id);

}
