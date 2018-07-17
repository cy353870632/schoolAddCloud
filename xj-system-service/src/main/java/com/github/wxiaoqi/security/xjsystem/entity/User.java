package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@TableName("system_user")
@Data
public class User extends Model<User>{

    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;

    private String u_name;//昵称

    private String pwd;

    private String l_name;//登录名

    private String role;//code,role关联

    private String image;//头像

    private String user_code;//用户角色，超级管理员999,不允许编辑，系统管理员998，推广员1

    private String phone;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}