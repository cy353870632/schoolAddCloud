package com.github.wxiaoqi.security.hrsystem.service.grid.impl;

import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.mapper.WebColumnsMapper;
import com.github.wxiaoqi.security.hrsystem.service.grid.IShareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * grid sql处理类
 * */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShareDataImpl implements IShareDataService {

    @Autowired
    WebColumnsMapper webColumnsMapper;
    /**
     * @param  commandText 原始sql   resultSql 带有别名的查询sql
     * @param  fields webcolms集合   totalSql  计算总查询解雇的count sql
     * */
    @Override
    public Map  GetColsInfoAndFormatSql(String commandText, Boolean customOrder, List<WebColumns> fields, String resultSql,String totalSql, Boolean essMode){
        try {
            commandText = commandText.replaceAll("select","SELECT");
            commandText = commandText.replaceAll(" from "," FROM ");
            commandText = commandText.replaceAll(" where "," WHERE ");
            commandText = commandText.replaceAll("inner join","INNER JOIN");
            commandText = commandText.replaceAll(" and "," AND ");
            commandText = commandText.replaceAll("order by","ORDER BY");
            return this.doSql(commandText,customOrder,fields,resultSql,totalSql,essMode);
        }catch (Exception e){
            return null;
        }
    }


    //带有join的语句处理
    private Map doSql(String commandText, Boolean customOrder, List<WebColumns> fields, String resultSql,String totalSql, Boolean essMode){
        totalSql ="SELECT count(*) FROM "+ commandText.split(" FROM")[1];
        if (totalSql.contains("ORDER BY")){
            totalSql = totalSql.split("ORDER BY")[0];
        }
        //带*的tablename
        resultSql = commandText;
        Map map = new HashMap<>();
        if (commandText.contains("INNER JOIN"))
            map = this.getClomsAndTableNameOnJoin(commandText);
        else
            map = this.getClomsAndTableNameNoJoin(commandText);
        List<Map> colmsList = (List<Map>)map.get("colms");
        List<Map> tableNameList = (List<Map>)map.get("tablenames");
        //带有*号的表
        String contXingtableName = "";
        //没有指定表名的字段，之后可以zaiwebcolm里面找
        String nofindTablenameColms = "";
        //没有找到表名的字段，给所有的表都加一遍去webcolms里面找
        String nofindTablenameTowebColms = "";
        if (colmsList == null || tableNameList == null ||
                colmsList.size()==0 || tableNameList.size() == 0){
        }else
        {
            //获取colmsList中殿额所有的key
            Set<String> colmskeys = colmsList.get(0).keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
            Set<String> tableNameKey = tableNameList.get(0).keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
            //吧对应的表和字段列出来
            List<Map> weblcolmssqlList = new ArrayList<>();
            for (String tableName:tableNameKey){
                Map nameAndColmsKeys = new HashMap<>();
                String colmsString = "";
                for(String colms:colmskeys){
                    if (colmskeys.size()==1 && colms.equals("*")){
                        colmsString = "'*'";
                        contXingtableName = tableName + ",";
                    }
                    else {
                        if (colms.equals(tableName) || colms.equals(tableNameList.get(0).get(tableName))
                                || colms.toLowerCase().equals(tableNameList.get(0).get(tableName))
                                || colms.toUpperCase().equals(tableNameList.get(0).get(tableName))) {
                            String[] colmsstatus = colmsList.get(0).get(colms).toString().split(",");
                            for (String s : colmsstatus) {
                                //这里处理一下别名sql
                                String oldSql = colms + "." + s+",";
                                if (!colmsList.get(0).get(colms).toString().contains("*"))
                                    resultSql = resultSql.replaceAll(oldSql, oldSql.substring(0,oldSql.length()-1) + " " + "'" + (tableName + "_" + s).toUpperCase() + "'"+",");
                                else {
                                    resultSql = resultSql.replaceAll(" " + colms + " ", " " + tableName + " ");
                                    resultSql = resultSql.replaceAll(colms + "\\.", tableName + "\\.");
                                    contXingtableName = tableName + ",";
                                }
                                colmsString = colmsString + "'" + s + "'" + ",";
                            }
                        } else {
                            if (colmsList.get(0).get(colms).equals("") && !nofindTablenameColms.contains(colms)) {
                                nofindTablenameColms = nofindTablenameColms + colms + ",";
                                nofindTablenameTowebColms = nofindTablenameTowebColms + "'" + colms + "'" + ",";
                            }
                        }
                    }
                }
                if (!colmsString.equals("'*'") && colmsString.length()>0)
                    colmsString = colmsString.substring(0,colmsString.length()-1);
                nameAndColmsKeys.put("tablename",tableName);
                nameAndColmsKeys.put("colmsname",colmsString);
                weblcolmssqlList.add(nameAndColmsKeys);
            }
            if (contXingtableName.length()>0){
                contXingtableName = contXingtableName.substring(0,contXingtableName.length()-1);
            }
            if (nofindTablenameColms.length()>0){
                nofindTablenameColms = nofindTablenameColms.substring(0,nofindTablenameColms.length()-1);
            }
            if (nofindTablenameTowebColms.length()>0){
                nofindTablenameTowebColms = nofindTablenameTowebColms.substring(0,nofindTablenameTowebColms.length()-1);
            }
            //拼接webcolms的语句
            String webcolmsSql = "select * from WebColumns where ";
            for (Map webclomsmap : weblcolmssqlList){
                String s = "( TableName="+ "'"+webclomsmap.get("tablename")+"'";
                if (nofindTablenameTowebColms.length()>0){
                    if (webclomsmap.get("colmsname").equals("'*'") || webclomsmap.get("colmsname").equals("")){
                        s = s + " and ColName in ( "+ nofindTablenameTowebColms+" ))";
                    }else
                    {
                        s = s + " and ColName in ( "+ webclomsmap.get("colmsname")+","+nofindTablenameTowebColms+" ))";
                    }
                }else
                {
                    if (webclomsmap.get("colmsname").equals("'*'") || webclomsmap.get("colmsname").equals("")){
                        s = s + ")";
                    }else
                    {
                        s = s + " and ColName in ( "+ webclomsmap.get("colmsname")+" ))";
                    }
                }
                webcolmsSql = webcolmsSql+s+" or ";
            }
            webcolmsSql = webcolmsSql.substring(0,webcolmsSql.length()-4);
            fields = webColumnsMapper.selctBySqlToWebColms(webcolmsSql);

        }
        //吧*替换成表名.*
        if (contXingtableName.length()>0 && !contXingtableName.equals("")){
            String[] s = contXingtableName.split(",");
            String sss = "";
            for (String ss:s){
                sss = sss+ss+".*"+",";
            }
            sss = sss.substring(0,sss.length()-1);
            resultSql = resultSql.replace(" * "," "+sss+" ");
        }
        Map resultMap = new HashMap<>();
        resultMap.put("fields",fields);
        resultMap.put("resultSql",resultSql);
        resultMap.put("totalSql",totalSql);
        resultMap.put("contXingtableName",contXingtableName);
        resultMap.put("nofindTablenameColms",nofindTablenameColms);
        return resultMap;
    }

    private Map getClomsAndTableNameOnJoin(String commandText){
        commandText = commandText.replace("SELECT ","");
        Map resultMao = new HashMap<>();
        List<Map> ColmsList = new ArrayList<>();
        List<Map> TableNameList = new ArrayList<>();
        String[] colms_list = commandText.split("FROM")[0].trim().split(",");
        //字段名的处理
        Map colmsListMap = new HashMap<>();
        for (String str : colms_list) {
            String[] colms_list1 = str.split("\\.");
            if (colmsListMap.get(colms_list1[0].trim())!=null){
                colmsListMap.put(colms_list1[0].trim(), colmsListMap.get(colms_list1[0].trim())+","+colms_list1[1].trim());
            }else {
                if (colms_list1.length == 1) {
                    colmsListMap.put(colms_list1[0].trim(), "");
                } else {
                    colmsListMap.put(colms_list1[0].trim(), colms_list1[1].trim());
                }
            }
        }
        ColmsList.add(colmsListMap);
        String[] table_list1 = null;
        //table in ()后面的list
        if (commandText.contains("WHERE"))
            table_list1 = commandText.split(" FROM ")[1].split(" WHERE ")[0].split("INNER JOIN");
        else
            table_list1 = commandText.split(" FROM ")[1].split("INNER JOIN ");
        //tablename 的处理
        Map tableNameListMap = new HashMap<>();
        for (String str : table_list1){
            //trim()去除字符串两端的空格
            String[] table_list2 = str.split(" ON ")[0].trim().split(" ");
            if (table_list2.length==1){
                tableNameListMap.put(table_list2[0].trim(),"");
            }else
            {
                tableNameListMap.put(table_list2[0].trim(),table_list2[1].trim());
            }
        }
        TableNameList.add(tableNameListMap);
        resultMao.put("colms",ColmsList);
        resultMao.put("tablenames",TableNameList);
        return resultMao;
    }

    private Map getClomsAndTableNameNoJoin(String commandText){
        commandText = commandText.replace("SELECT ","");
        Map resultMao = new HashMap<>();
        List<Map> ColmsList = new ArrayList<>();
        List<Map> TableNameList = new ArrayList<>();
        String[] colms_list = commandText.split("FROM")[0].trim().split(",");
        //字段名的处理
        Map colmsListMap = new HashMap<>();
        for (String str : colms_list) {
            String[] colms_list1 = str.split("\\.");
            if (colmsListMap.get(colms_list1[0].trim())!=null){
                colmsListMap.put(colms_list1[0].trim(), colmsListMap.get(colms_list1[0].trim())+","+colms_list1[1].trim());
            }else {
                if (colms_list1.length == 1) {
                    colmsListMap.put(colms_list1[0].trim(), "");
                } else {
                    colmsListMap.put(colms_list1[0].trim(), colms_list1[1].trim());
                }
            }
        }
        ColmsList.add(colmsListMap);
        String[] table_list1 = null;
        //table in ()后面的list
        if (commandText.contains("WHERE"))
            table_list1 = commandText.split(" FROM ")[1].split(" WHERE ")[0].trim().split(",");
        else
            table_list1 = commandText.split(" FROM ")[1].trim().split(",");
        //tablename 的处理
        Map tableNameListMap = new HashMap<>();
        for (String str : table_list1){
            if (str.contains("\\.")){
                tableNameListMap.put(str.split("\\.")[0],str.split("\\.")[1]);
            }else
            {
                tableNameListMap.put(str,"");
            }
        }
        TableNameList.add(tableNameListMap);
        resultMao.put("colms",ColmsList);
        resultMao.put("tablenames",TableNameList);
        return resultMao;
    }
}