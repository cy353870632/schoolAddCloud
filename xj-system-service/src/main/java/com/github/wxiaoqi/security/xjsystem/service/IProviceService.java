package com.github.wxiaoqi.security.xjsystem.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.entity.Role;
import com.github.wxiaoqi.security.xjsystem.vo.ProvinceVo;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IProviceService extends IService<Province> {

    public List<ProvinceVo> getAllCityByStatus(Integer status);

}
