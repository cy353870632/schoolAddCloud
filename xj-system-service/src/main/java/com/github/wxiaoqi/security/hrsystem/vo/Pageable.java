package com.github.wxiaoqi.security.hrsystem.vo;

import lombok.Data;

import java.util.*;

/**
 * 工作桌面vo
 *
 * @author chengyuan
 * @create 2018-05-22 15:21
 */
@Data
public class Pageable {
    public Boolean alwaysVisible=true;
    public int currentPage;
    public PageableMessage message = new PageableMessage();
    public List<String> pageSizes =new ArrayList<>(Arrays.asList("10","20","30","40"));
    private int pageSize;
    private int total;
}
