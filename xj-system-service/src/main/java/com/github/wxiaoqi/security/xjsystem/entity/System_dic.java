package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//角色和菜单关联类
@TableName("system_dic")
@Data
public class System_dic extends Model<System_dic> implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String dic_name;//字典名称
    private String parent_id;//父id
    private String dic_code;//字典编码
    private String status;//状态，1为可用，0位不可用
    //工号，账号
    private Date creat_date;
    private Date update_date;
    //为1的时候不允许修改，为系统数据，为0的时候可以编辑
    private String read_only;
    private String parent_title;//父级名称
    private String end_mark;//是否为终结点

    @Override
    protected Serializable pkVal() {
        return null;
    }
}