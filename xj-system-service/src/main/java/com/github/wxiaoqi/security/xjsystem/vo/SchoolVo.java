package com.github.wxiaoqi.security.xjsystem.vo;

import com.github.wxiaoqi.security.xjsystem.entity.School;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class SchoolVo{
    private String id;
    private String s_name;//学校名称
    private String address;//地址
    private String phone;//联系人电话
    private String s_user_name;//学校联系人
    //工号，账号
    private Date creat_date;
    private String status;//状态，1为可用，0位不可用
    private String city;//所在城市
    private String description;//描述
    private String president;//校长名称
    public String style_name;
    public String school_creat_name;
}
