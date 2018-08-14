package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//角色和菜单关联类
@TableName("system_school")
@Data
public class School extends Model<School> implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String s_name;//学校名称
    private String address;//地址
    private String phone;//联系人电话
    private String s_user_name;//学校联系人

    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;
    private String status;//状态，1为可用，0位不可用
    private String province;//所在省份
    private String area;//所在区县
    private String city;//所在城市
    private String style;//类型，编码，对应dic中的schoolStyle
    private String description;//描述
    private String president;//校长名称
    private String school_creat;//办学类型，编码，对应dic中的schoolCreat
    private int review_status;//审批状态，1为通过，0位未通过，2为待审批
    private String creat_user;//描述
    private String review_user;//校长名称
    private double longitude;//经度
    private double latitude;//纬度


    @Override
    protected Serializable pkVal() {
        return null;
    }
}