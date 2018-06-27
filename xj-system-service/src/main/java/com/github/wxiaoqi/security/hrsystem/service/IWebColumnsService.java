package com.github.wxiaoqi.security.hrsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IWebColumnsService extends IService<WebColumns> {

    public List<WebColumns> selectByTablename(String tablename);

    public Object selectPanelVoByTablename(String tablename,String primaryKey,int k_id,int A0188,String userName);


}
