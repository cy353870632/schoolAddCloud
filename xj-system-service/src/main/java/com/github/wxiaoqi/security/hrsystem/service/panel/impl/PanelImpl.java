package com.github.wxiaoqi.security.hrsystem.service.panel.impl;

import com.github.wxiaoqi.security.common.util.MD5Util;
import com.github.wxiaoqi.security.hrsystem.entity.PanelOpt;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.mapper.UtilMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebColumnsMapper;
import com.github.wxiaoqi.security.hrsystem.service.grid.IShareDataService;
import com.github.wxiaoqi.security.hrsystem.service.panel.IPanelService;
import com.github.wxiaoqi.security.hrsystem.utils.ArrayListType;
import com.github.wxiaoqi.security.hrsystem.utils.BeansUtils;
import com.github.wxiaoqi.security.hrsystem.utils.CodeUtils;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;
import com.github.wxiaoqi.security.hrsystem.vo.PanelDataVo;
import com.github.wxiaoqi.security.hrsystem.vo.PanelInterface;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * grid sql处理类
 * */
@Service
@Transactional(rollbackFor = Exception.class)
public class PanelImpl implements IPanelService {

    @Autowired
    WebColumnsMapper webColumnsMapper;

    @Autowired
    UtilMapper utilMapper;

    //获取panel，新增和编辑
    @Override
    public Object getPaneByTableNameAndKid(String tablename, String primaryKey, int k_id, int A0188, String userName) {
        List<PanelVo> panlVofindList = webColumnsMapper.selectPanelVoByTablename(tablename);

        if (panlVofindList == null || panlVofindList.size() == 0){
            return null;
        }
        List<PanelVo> panlVoHandleList = new ArrayList<>();
        List<PanelVo> panlVoList = new ArrayList<>();
        CodeUtils codeUtils = new CodeUtils();
        //不区分key的大小写
        Map oldDataMap = new CaseInsensitiveMap();
        if (k_id == 0){

        }else {
            List<CaseInsensitiveMap> oldDataList = utilMapper.selectBysqlForCaseInsensitiveMap("select * from "+tablename+" where "+primaryKey+"="+k_id);
            if (oldDataMap==null || oldDataList.size()==0)
                return null;
            else
                oldDataMap = oldDataList.get(0);
        }
        //筛选所有的字段
        for (PanelVo panelVo:panlVofindList){
            panelVo.setColAliasName(panelVo.getTableName()+"_"+panelVo.getColName());
            PanelDataVo panelDataVo = new PanelDataVo();
            if (panelVo.getEditFormat() != null && panelVo.getEditFormat().contains("CODE")){
                panelVo.setType("DropDownList");
                String sqlname = panelVo.getEditFormat().replace("CODE(","");
                sqlname = sqlname.replace(")","");
                sqlname = sqlname.split("\\|")[0];
                List<Map> datacode = codeUtils.codeToMap(sqlname,utilMapper);
                panelDataVo.setItems(datacode);
                panelDataVo.setSelectedValue("");
            }
            else if (panelVo.getEditFormat() != null && panelVo.getEditFormat().contains("LOOK")){
                panelVo.setType("DropDownList");
                String sqlname = panelVo.getEditFormat().replace("LOOK(","");
                sqlname = sqlname.replace(")","");
                String[] strList = sqlname.split("\\|");
                List<Map> datacode = codeUtils.codeToMapByStrList(strList,utilMapper,A0188);
                panelDataVo.setItems(datacode);
                panelDataVo.setOldValue(A0188+"");
                panelDataVo.setSelectedValue(A0188+"");
            }
            else if (panelVo.getEditFormat() != null && panelVo.getEditFormat().contains("CHECK")){
                panelVo.setType("CheckBox");
//                panelDataVo.setChecked(true);
            }
            else if (panelVo.getEditFormat() == null || panelVo.getEditFormat().equals(""))
            {
                if ( panelVo.getColType().contains("DATETIME")){
                    panelVo.setType("Date");
                }else
                    panelVo.setType("TextBox");
            }
            else
            {
                if (panelVo.getEditFormat().contains("DATETIME"))
                    panelVo.setType("DateTime");
                else if (panelVo.getEditFormat().contains("TIME"))
                    panelVo.setType("Time");
                else
                    panelVo.setType(panelVo.getEditFormat().split("\\(")[0]);

            }
            if (panelVo.getColVisible() == 2){
                panelVo.setEnabled(false);
            }
            if (k_id!=0){
                try {
                    String oldvalue = oldDataMap.get(panelVo.getColName()).toString();
                    panelDataVo.setOldValue(oldvalue==null?"":oldvalue);
                    if (panelVo.getType().equals("CheckBox")){
                        if (panelDataVo.getOldValue().equals("1"))
                            panelDataVo.setChecked(true);
                    }else if (panelVo.getType().equals("DropDownList"))
                    {
                        panelDataVo.setSelectedValue(oldvalue==null?"":oldvalue);
                    }else if (panelVo.getType().equals("Date") || panelVo.getType().equals("DateTime") || panelVo.getType().equals("Time"))
                    {
                        panelDataVo.setTime(oldvalue==null?"":oldvalue);
                    }
                    else if (panelVo.getType().equals("TextBox"))
                    {
                        panelDataVo.setText(oldvalue==null?"":oldvalue);
                    }
                }catch (Exception e){
                    panelDataVo.setOldValue("");
                }
            }
            panelVo.setData(panelDataVo);
            if (panelVo.getColVisible()==0){
                panelVo.setType("Hidden");
                panlVoHandleList.add(panelVo);
            }
            if (panelVo.getColVisible()==2 && panelVo.getEditFormat()!=null){
                if (panelVo.getData().getOldValue()==null){
                    if (panelVo.getEditFormat().contains("LOOK") && (panelVo.getEditFormat().contains("A0188") || panelVo.getEditFormat().contains("a0188"))){
                        PanelDataVo panelDataVo1 = panelVo.getData();
                        panelDataVo1.setOldValue(A0188+"");
                        panelVo.setData(panelDataVo1);
                    }
                    if (panelVo.getEditFormat().contains("CODE")){
                        PanelDataVo panelDataVo1 = panelVo.getData();
                        String s = panelVo.getEditFormat().split("\\|")[1];
                        panelDataVo1.setOldValue(s.substring(0,s.length()-1));
                        panelDataVo1.setSelectedValue(s.substring(0,s.length()-1));
                        panelVo.setData(panelDataVo1);
                    }
                }
            }
            if (panelVo.getColName().equals("OPDATE")){
                PanelDataVo panelDataVo1 = panelVo.getData();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                panelDataVo1.setTime(sdf.format(new Date()));
                panelVo.setData(panelDataVo1);
            }
            if (panelVo.getColName().equals("OPNAME")){
                PanelDataVo panelDataVo1 = panelVo.getData();
                panelDataVo1.setText(userName);
                panelVo.setData(panelDataVo1);
            }
            panlVoList.add(panelVo);
        }
        Map<String,ArrayList> ss =new ArrayListType().sort(panlVoList);
        java.util.Iterator it = ss.entrySet().iterator();
        List groups = new ArrayList<>();
        while(it.hasNext()){
            java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
            Map resultmap = new HashMap<>();
            resultmap.put("name",entry.getKey());
            resultmap.put("fields",entry.getValue());
            groups.add(resultmap);
        }
        Map uptademap = new HashMap<>();
        uptademap.put("tableName",tablename);
        uptademap.put("updateCond",primaryKey+"="+k_id);

        PanelOpt panelOpt = new PanelOpt();
        panelOpt.setGroups(groups);
        panelOpt.setAllowGroup(true);
        panelOpt.setEnabled(true);
        panelOpt.setHiddenFields(panlVoHandleList);
        panelOpt.setPanelInterface(new PanelInterface());
        panelOpt.setUpdateTables(uptademap);
        panelOpt.setPanelKey(this.getpanelKey(groups,panlVoHandleList));
        return panelOpt;
    }

    @Override
    public Boolean updatepanel(PanelOpt panelOpt,Integer A0188){
        String panelKey = panelOpt.getPanelKey();
        Boolean allowGroup = panelOpt.getAllowGroup();
        Boolean enabled = panelOpt.getEnabled();
        List<Map> groups = panelOpt.getGroups();
        List<PanelVo> hiddenFields = panelOpt.getHiddenFields();
        PanelInterface panelInterface = panelOpt.getPanelInterface();
        Map updateTables = panelOpt.getUpdateTables();

        Map statusMap = this.enterpanelKey(groups,hiddenFields);
        if (statusMap.get("key").equals(panelKey)) {
            //表名
            String tablename = panelOpt.getUpdateTables().get("tableName").toString();
            //查询条件
            String pamyId = panelOpt.getUpdateTables().get("updateCond").toString();
            //然后拼接语句
            List<PanelVo> panelVoList = (List<PanelVo>)statusMap.get("group");
            String colms = "";
            for (PanelVo panelVo: panelVoList){
               // "DropDownList"下拉类型 ---  selectedValue
                // Date类型 ----- time "
                // DateTime" ---- time
                // "TextBox" ---- text
                //"Time" -----   time
                if (panelVo.getColVisible()==1) {
                    if (panelVo.getType().equals("Date") || panelVo.getType().equals("DateTime") ||
                            panelVo.getType().equals("Time")) {
                        if (panelVo.getData().getTime() != null)
                            colms = colms + panelVo.getColName() + "='" + panelVo.getData().getTime() + "',";
                    } else if (panelVo.getType().equals("TextBox")) {
                        if (panelVo.getData().getText() != null)
                            colms = colms + panelVo.getColName() + "='" + panelVo.getData().getText() + "',";
                    } else if (panelVo.getType().equals("DropDownList")) {
                        if (panelVo.getData().getSelectedValue() != null)
                            colms = colms + panelVo.getColName() + "='" + panelVo.getData().getSelectedValue() + "',";
                    }
                }
            }
            colms = colms.substring(0,colms.length()-1);
            String sql = "UPDATE "+tablename+" SET "+colms+" where "+pamyId;
            return webColumnsMapper.updateBySql(sql);
        }
        return false;
    }

    @Override
    public Boolean savepanel(PanelOpt panelOpt, Integer A0188) {
        String panelKey = panelOpt.getPanelKey();
        Boolean allowGroup = panelOpt.getAllowGroup();
        Boolean enabled = panelOpt.getEnabled();
        List<Map> groups = panelOpt.getGroups();
        List<PanelVo> hiddenFields = panelOpt.getHiddenFields();
        PanelInterface panelInterface = panelOpt.getPanelInterface();
        Map updateTables = panelOpt.getUpdateTables();

        Map statusMap = this.enterpanelKey(groups,hiddenFields);
        if (statusMap.get("key").equals(panelKey)) {
            //表名
            String tablename = panelOpt.getUpdateTables().get("tableName").toString();
            String colNameList = "";
            String colValueList = "";
            //然后拼接语句
            List<PanelVo> panelVoList = (List<PanelVo>)statusMap.get("group");
            for (PanelVo panelVo: panelVoList){
                // "DropDownList"下拉类型 ---  selectedValue
                // Date类型 ----- time "
                // DateTime" ---- time
                // "TextBox" ---- text
                //"Time" -----   time
                if (panelVo.getType().equals("Date") || panelVo.getType().equals("DateTime") ||
                        panelVo.getType().equals("Time")) {
                    if (panelVo.getData().getTime() != null) {
                        colNameList = colNameList + "" + panelVo.getColName() + ",";
                        colValueList = colValueList + "'" + panelVo.getData().getTime() + "',";
                    }
                } else if (panelVo.getType().equals("TextBox")) {
                    if (panelVo.getData().getText() != null && !panelVo.getData().getText().equals("")){
                        colNameList = colNameList + "" + panelVo.getColName() + ",";
                        colValueList = colValueList + "'" + panelVo.getData().getText() + "',";
                    }
                } else if (panelVo.getType().equals("DropDownList")) {
                    if (panelVo.getData().getSelectedValue() != null){
                        colNameList = colNameList + "" + panelVo.getColName() + ",";
                        colValueList = colValueList + "'" + panelVo.getData().getSelectedValue() + "',";
                    }
                }
            }
            colNameList = colNameList.substring(0,colNameList.length()-1);
            colValueList = colValueList.substring(0,colValueList.length()-1);

            String sql = "INSERT INTO "+tablename+" ("+colNameList+" ) VALUES ("+colValueList+")";
            return webColumnsMapper.insertBySql(sql);
        }
        return false;
    }


    @Override
    public String getpanelKey( List<Map> groups,List<PanelVo> hiddenFields){
        List<PanelVo> panelVos = new ArrayList<>();
        for (Map map: groups){
            List<PanelVo> ppp = (List<PanelVo>)map.get("fields");
            panelVos.addAll(ppp);
        }
        panelVos.addAll(hiddenFields);
        String panelkey = "";
        for (PanelVo panelVo : panelVos){
            panelkey = panelkey+panelVo.getColAliasName()+"#"+panelVo.getColRight()+"#"+panelVo.getColVisible()+"#";
        }
        return MD5Util.encrypt(panelkey);
    }


    public Map enterpanelKey( List<Map> groups,List<PanelVo> hiddenFields){
        Map resultMap = new HashMap<>();
        try {
            List<PanelVo> panelVos = new ArrayList<>();
            for (Map map: groups){
                List<PanelVo> ppp = new ArrayList<>();
                for (Map map1 :  (List<Map>)map.get("fields")){
                    PanelVo panelVo = BeansUtils.map(map1,PanelVo.class);
                    ppp.add(panelVo);
                }
                panelVos.addAll(ppp);
            }
            panelVos.addAll(hiddenFields);
            String panelkey = "";
            for (PanelVo panelVo : panelVos){
                panelkey = panelkey+panelVo.getColAliasName()+"#"+panelVo.getColRight()+"#"+panelVo.getColVisible()+"#";
            }
            resultMap.put("key",MD5Util.encrypt(panelkey));
            resultMap.put("group",panelVos);

            return resultMap;
        }catch (Exception e){
            return null;
        }
    }
}