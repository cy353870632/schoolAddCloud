package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengyuan
 * @create 2018-08-10 10:43
 * @desc 城市
 **/
@TableName("city")
@Data
public class City extends Model<City> implements Serializable{

    @TableId
    private String id;

    private String code;
    private String name;
    private String provincecode;
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return null;
    }
}
