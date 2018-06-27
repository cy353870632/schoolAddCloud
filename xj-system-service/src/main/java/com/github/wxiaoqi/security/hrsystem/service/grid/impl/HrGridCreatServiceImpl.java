package com.github.wxiaoqi.security.hrsystem.service.grid.impl;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.mapper.WebColumnsMapper;
import com.github.wxiaoqi.security.hrsystem.service.grid.ICacheService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IHrGridCreatService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IShareDataService;
import com.github.wxiaoqi.security.hrsystem.vo.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 字段查询类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class HrGridCreatServiceImpl extends ServiceImpl<WebColumnsMapper,WebColumns> implements IHrGridCreatService {


    @Autowired
    WebColumnsMapper webColumnsMapper;

    @Autowired
    IShareDataService shareDataService;

    @Autowired
    ICacheService cacheService;

    private List<Map> cacheWebColumns;
    private String cacheTotalSql;
    private String cacheResultSql;
    private String keyField;

    @Override
    public Object  creatHrGrid(GridOpt gridOpt,Integer a0188){
        Map resultMap = new HashMap<>();
        List<WebColumns> fields = new ArrayList<>();
        String resultSql = "";
        String totalSql = "";
        Map sqlMap = shareDataService.GetColsInfoAndFormatSql(gridOpt.getCommandText(),false,fields,resultSql,totalSql,false);
        fields = (List<WebColumns>)sqlMap.get("fields");
        resultSql = (String)sqlMap.get("resultSql");
        totalSql = (String)sqlMap.get("totalSql");
        //查找全部字段的表名
        String contXingTableName = (String)sqlMap.get("contXingtableName");
        //没有找到表名的字段
        String nofindTablenameColms = (String)sqlMap.get("nofindTablenameColms");
        if (contXingTableName!=null && !contXingTableName.equals("")){
            String[] str = contXingTableName.split(",");
            //带星号的所有字段的别名
            String s = "";
            for (String s1 : str){
                List<WebColumns> webColumnList = fields.stream().filter(webColms1 -> webColms1.getTableName().equals(s1)|| webColms1.getTableName().equals(s1.toLowerCase())|| webColms1.getTableName().equals(s1.toUpperCase())).collect(Collectors.toList());
                for (WebColumns webColumns : webColumnList){
                    s = s + s1+"."+webColumns.getColName()+" "+"'"+(s1+"_"+webColumns.getColName()).toUpperCase()+"'"+",";
                }
                s = s.substring(0,s.length()-1);
                resultSql = resultSql.replace(s1+".*",s);
            }
        }
        if (nofindTablenameColms.length()>0 && !nofindTablenameColms.equals("")){
            String[] nofindTablenameTowebColm = nofindTablenameColms.split(",");
            for (String s: nofindTablenameTowebColm){
                List<WebColumns> webColumnList = fields.stream().filter(webColms1 -> webColms1.getColName().equals(s)).collect(Collectors.toList());
                if (webColumnList.size()>0){
                    resultSql = resultSql.replace(s+",",s+" '"+(webColumnList.get(0).getTableName()+"_"+webColumnList.get(0).getColName()).toUpperCase()+"',");
                }
            }
        }
        List colmsList = new ArrayList<>();
        for (WebColumns webColumns:fields){
                Map webcolmsMap = new HashMap<>();
            webcolmsMap.put("prop",webColumns.getTableName()+"_"+webColumns.getColName());
            webcolmsMap.put("label",webColumns.getDisplayLabel());
            webcolmsMap.put("width",webColumns.getDisplayWidth()+"00px");
            if (webColumns.getEditFormat()!=null){
                if (webColumns.getEditFormat().contains("CHECK")){
                    webcolmsMap.put("type","check");
                }
                else if (webColumns.getEditFormat().contains("TIME")){
                    webcolmsMap.put("type","time");
                }
                else if (webColumns.getEditFormat().contains("LINK")){
                    webcolmsMap.put("type","link");
                }else
                {
                    webcolmsMap.put("type","literal");
                }
            }else{
                webcolmsMap.put("type","literal");
            }
            colmsList.add(webcolmsMap);
        }

        //先清除缓存
        cacheService.clearCache(gridOpt.getGridKey(),a0188);
        //然后在加分页的前面就把信息给缓存起来
        cacheService.getCacheMessage(colmsList,totalSql,resultSql,keyField);
        cacheService.cacheGridMessage(gridOpt.getGridKey(),a0188);

        //给resultSql加第一层分页
        int pageSize = gridOpt.getPageSize()==null?10:gridOpt.getPageSize();
        if (resultSql.contains("ORDER BY") || resultSql.contains("order by"))
            resultSql = resultSql+" "+" OFFSET 0 ROWS FETCH NEXT "+pageSize+" ROWS ONLY";
        else
            resultSql = resultSql+" "+" ORDER BY 1  OFFSET 0 ROWS FETCH NEXT "+pageSize+" ROWS ONLY";

        List<Map> totalMap = webColumnsMapper.selctBySqlToMap(totalSql);

        List<Map> dataScourceMap = webColumnsMapper.selctBySqlToMap(resultSql);

        resultMap.put("columns",colmsList);
        resultMap.put("gridKey",gridOpt.getGridKey());
        resultMap.put("keyField",gridOpt.getKeyField());
        resultMap.put("editable",false);
        resultMap.put("deleteable",false);
        resultMap.put("selectable",true);
        Pageable pageable = new Pageable();
        pageable.setPageSize(gridOpt.getPageSize()==null?10:gridOpt.getPageSize());
        pageable.setCurrentPage(1);
        pageable.setTotal((int)totalMap.get(0).get(""));
        resultMap.put("pageable",pageable);
        resultMap.put("dataSource",dataScourceMap);
        resultMap.put("success",true);

        return resultMap;
    }

    @Override
    public Object getPageData(String gridKey, Integer A0188,Integer pageSize,Integer currentPage) {
        Map resultMap = new HashMap<>();
        Map cacheMap =  cacheService.cacheGridMessage(gridKey,A0188);
        if (cacheMap == null){
            return new Exception();
        }
        List<Map> fields = (List<Map>)cacheMap.get("fields");
        String totalSql = (String)cacheMap.get("totalSql");
        String resultSql = (String)cacheMap.get("resultSql");
        String keyField = (String)cacheMap.get("keyField");
        //给resultSql加第一层分页
        if (resultSql.contains("ORDER BY") || resultSql.contains("order by"))
            resultSql = resultSql+" "+" OFFSET " +((currentPage-1)*pageSize)+ " ROWS FETCH NEXT "+pageSize+" ROWS ONLY";
        else
            resultSql = resultSql+" "+" ORDER BY 1  OFFSET " +((currentPage-1)*pageSize)+ " ROWS FETCH NEXT "+pageSize+" ROWS ONLY";
        List<Map> totalMap = webColumnsMapper.selctBySqlToMap(totalSql);
        List<Map> dataScourceMap = webColumnsMapper.selctBySqlToMap(resultSql);

        resultMap.put("columns",fields);
        resultMap.put("gridKey",gridKey);
        resultMap.put("keyField",keyField);
        resultMap.put("editable",false);
        resultMap.put("deleteable",false);
        resultMap.put("selectable",true);
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal((int)totalMap.get(0).get(""));
        resultMap.put("pageable",pageable);
        resultMap.put("dataSource",dataScourceMap);
        resultMap.put("success",true);
        return resultMap;
    }


}
