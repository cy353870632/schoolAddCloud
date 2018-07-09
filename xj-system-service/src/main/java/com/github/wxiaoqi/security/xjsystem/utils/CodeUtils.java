package com.github.wxiaoqi.security.xjsystem.utils;

import com.github.wxiaoqi.security.xjsystem.mapper.UtilMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyuan
 * @create 2018-05-24 16:53
 * @desc panel中的code处理
 **/
public class CodeUtils {


    public List<Map> codeToMap(String tablename, UtilMapper utilMapper){
        String sql = "select * from "+tablename;
        List<Map> sqldata = utilMapper.selectBysql(sql);
        List<Map> result = new ArrayList<>();
        for (Map map : sqldata){
            Map resultMap = new HashMap<>();
            resultMap.put("key",map.get("BM0000")+"");
            resultMap.put("value",map.get("MC0000"));
            result.add(resultMap);
        }
        Map resultMap = new HashMap<>();
        resultMap.put("key","");
        resultMap.put("value","----------");
        result.add(resultMap);
        return result;
    }

    public List<Map> codeToMapByStrList(String[] sqlList, UtilMapper utilMapper,int A0188){
        List<Map> result = new ArrayList<>();
        Map resultMap = new HashMap<>();
        resultMap.put("key","");
        resultMap.put("value","----------");
        String tablename = "";
        String colList = "";
        for (int i =0;i < sqlList.length;i++){
            if (i == 0){
                tablename = sqlList[i];
            }else
            {
                colList = colList + sqlList[i] + ",";
            }
        }
        colList = colList.substring(0,colList.length()-1);
        String sqlstr = "select "+colList +" from "+tablename +" where A0188="+A0188;
        List<Map> sqldata = utilMapper.selectBysql(sqlstr);
        for (Map map : sqldata){
            Map result1Map = new HashMap<>();
            result1Map.put("key",map.get(sqlList[1])+"");
            result1Map.put("value",map.get(sqlList[2])+"");
            result.add(result1Map);
        }
        result.add(resultMap);
        return result;
    }

}
