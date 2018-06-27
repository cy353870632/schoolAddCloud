package com.github.wxiaoqi.security.hrsystem.vo;

import com.github.wxiaoqi.security.common.vo.TreeNode;
import lombok.Data;

/**
 * 个人信息，default接口的vo
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class UserInfoVo{
    //姓名，A0101
    public String name;
    //部门 DEPT_CODE
    public String deptName;
    //头像：写死PublicDlg/ShowPhoto.aspx?EmployeeID=  A0188
    public String images;
    //消息总数：webmessagemap表中所有a0188匹配数据
    private int messageCount;
    //默认桌面页面：写死。。。
    private String defaltPage = "Desktop/workDesktop";
    //新消息数：webmessagemap所有readtag为0的
    private int numNewMessage;;
    //写死。。。为1 ！不要问我为什么，我也不知道！
    private String urlFunTag = "1";
}
