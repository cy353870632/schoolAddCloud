package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//角色和菜单关联类
@TableName("system_menu_role")
@Data
public class MenuRole extends Model<MenuRole> implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;
    private String menu_id;//菜单名称
    private String role_id;//角色名称

    @Override
    protected Serializable pkVal() {
        return null;
    }
}