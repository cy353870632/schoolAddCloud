package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@TableName("system_role")
@Data
public class Role extends Model<Role> {
    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;

    private String r_name;//角色名称

    private String sort;//排序，依照权限大小分配，权限越大，排序越大

    private String r_name_china;//角色名称中文


    @Override
    protected Serializable pkVal() {
        return null;
    }
}