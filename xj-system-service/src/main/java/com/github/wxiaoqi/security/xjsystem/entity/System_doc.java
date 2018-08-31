package com.github.wxiaoqi.security.xjsystem.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//角色和菜单关联类
@TableName("system_doc")
@Data
public class System_doc extends Model<System_doc> implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String user_name;//操作者名称
    private String user_id;//操作者id
    private String active;//行为
    private String level;//行为等级，普通/敏感
    private Date do_time;//操作时间

    @Override
    protected Serializable pkVal() {
        return null;
    }
}