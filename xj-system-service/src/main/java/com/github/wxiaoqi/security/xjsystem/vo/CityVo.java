package com.github.wxiaoqi.security.xjsystem.vo;

import com.github.wxiaoqi.security.xjsystem.entity.Area;
import com.github.wxiaoqi.security.xjsystem.entity.City;
import com.github.wxiaoqi.security.xjsystem.entity.Province;
import lombok.Data;

import java.util.List;

/**
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class CityVo extends City{
    public List<Area> children;
}
