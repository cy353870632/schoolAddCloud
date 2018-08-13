package com.github.wxiaoqi.security.xjsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import com.github.wxiaoqi.security.xjsystem.vo.CityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper extends BaseMapper<City> {
    public List<CityVo> getcityByProCodeList(@Param("proCodeList") List<String> proCodeList);
    public List<CityVo> getcityByProCode(@Param("proCode") String proCodeList);

}