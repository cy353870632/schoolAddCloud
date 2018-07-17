package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

//角色和菜单关联类
@TableName("system_menu")
@Data
public class Menu extends Model<Menu> {
    @TableId
    private String id;
    //工号，账号
    private Date creat_date;

    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;
    private String title;//菜单名称
    private String parent_id;//父id
    private String end_mark;//是否为终结点
    private String state;//状态，1为可用，0位不可用
    private String code_path;//跳转的路由路径

    @Override
    protected Serializable pkVal() {
        return null;
    }
}