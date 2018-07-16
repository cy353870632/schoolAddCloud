package com.github.wxiaoqi.security.xjsystem.vo;

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
    public Integer currentPage;
    public List<String> pageSizes =new ArrayList<>(Arrays.asList("10","20","30","40"));
    private Integer pageSize;
    private Integer total;
}
