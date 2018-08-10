package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chengyuan
 * @create 2018-08-10 10:36
 * @desc 省份
 **/
@TableName("province")
@Data
public class Province extends Model<Province> implements Serializable{

    @TableId
    private String id;

    private String code;
    private String name;
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
