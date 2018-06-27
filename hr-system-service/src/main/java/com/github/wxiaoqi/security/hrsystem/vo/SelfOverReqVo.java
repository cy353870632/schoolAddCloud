package com.github.wxiaoqi.security.hrsystem.vo;

import lombok.Data;

/**
 *
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class SelfOverReqVo {

    //開始時間
    public String over_Begin;
    //結束時間
    public String over_End;
    //狀態
    public String signed;
    //頁碼
    private String currentPage;
    //每頁數目
    private String  pageSize;

}
