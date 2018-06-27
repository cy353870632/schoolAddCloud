package com.github.wxiaoqi.security.hrsystem.service.grid;

import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IHrGridCreatService {

    public Object  creatHrGrid(GridOpt gridOpt, Integer a0188);

    public Object getPageData(String gridKey,Integer A0188,Integer pageSize,Integer currentPage);

}
