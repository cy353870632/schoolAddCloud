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

    public List<System_dic> getChildByParentid(String parent_id);

    public List<System_dic> getChildByDicName(String dic_name);

    public void cacheClear(String parent_id);

    public Integer addSysDic(System_dic system_dic);

    public List getAllParent();

    public Integer upSysDic(System_dic system_dic,Integer status);

}
