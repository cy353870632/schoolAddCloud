package com.github.wxiaoqi.security.xjsystem.vo;

import lombok.Data;

/**
 * 工作桌面vo
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class PageableMessage {

    public String display = "{0} - {1} 共 {2} 条数据";
    public String empty = "没有要显示的数据";
    public String first = "首页";
    public String itemsPerPage = "items per page";
    public String last = "最后一页";
    public String next = "下一页";
    public String of = "of {0}";
    public String page = "Page";
    public String previous = "前一页";
    public String refresh = "刷新";


}
