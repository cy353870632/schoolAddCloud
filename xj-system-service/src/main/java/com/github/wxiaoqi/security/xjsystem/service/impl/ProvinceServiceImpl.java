package com.github.wxiaoqi.security.xjsystem.service.impl;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.xjsystem.entity.Area;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.mapper.AreaMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.CityMapper;
import com.github.wxiaoqi.security.xjsystem.mapper.ProvinceMapper;
import com.github.wxiaoqi.security.xjsystem.service.IProviceService;
import com.github.wxiaoqi.security.xjsystem.vo.CityVo;
import com.github.wxiaoqi.security.xjsystem.vo.ProvinceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 省
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper,Province> implements IProviceService{
    @Autowired
    ProvinceMapper provinceMapper;
    @Autowired
    CityMapper cityMapper;
    @Autowired
    AreaMapper areaMapper;

    @Override
//    @Cache(key = "citystatus:u{1}")
    public List<ProvinceVo> getAllCityByStatus(Integer status) {
        List<ProvinceVo> provinceVoList = provinceMapper.selectAllByStatus(status);
        for (ProvinceVo provinceVo:provinceVoList){
            List<CityVo> cityList = cityMapper.getcityByProCode(provinceVo.getCode());
            for (CityVo cityVo:cityList){
                EntityWrapper<Area> wrapper = new EntityWrapper<Area>();
                wrapper.eq("citycode",cityVo.getCode());
                wrapper.eq("status",1);
                List<Area> areaList = areaMapper.selectList(wrapper);
                cityVo.setChildren(areaList);
            }
            provinceVo.setChildren(cityList);
        }
        return provinceVoList;
    }
}
