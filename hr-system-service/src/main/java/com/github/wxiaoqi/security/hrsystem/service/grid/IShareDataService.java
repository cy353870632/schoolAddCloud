package com.github.wxiaoqi.security.hrsystem.service.grid;

import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 16:23
 */

public interface IShareDataService {
    public Map  GetColsInfoAndFormatSql(String commandText, Boolean customOrder, List<WebColumns> fields, String resultSql, String totalSql, Boolean essMode);
}
