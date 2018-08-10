package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengyuan
 * @create 2018-08-10 10:43
 * @desc 区县
 **/
@TableName("area")
@Data
public class Area extends Model<Area> implements Serializable{

    @TableId
    private String id;

    private String code;
    private String name;
    private String citycode;
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return null;
    }
}
