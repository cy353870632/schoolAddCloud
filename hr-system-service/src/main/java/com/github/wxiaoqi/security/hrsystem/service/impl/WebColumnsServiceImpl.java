package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.entity.WebMessageMap;
import com.github.wxiaoqi.security.hrsystem.mapper.UserMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.UtilMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebColumnsMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebModuleInfoMapper;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebColumnsService;
import com.github.wxiaoqi.security.hrsystem.service.IWebMessageMapService;
import com.github.wxiaoqi.security.hrsystem.utils.ArrayListType;
import com.github.wxiaoqi.security.hrsystem.utils.CodeUtils;
import com.github.wxiaoqi.security.hrsystem.vo.PanelDataVo;
import com.github.wxiaoqi.security.hrsystem.vo.PanelInterface;
import com.github.wxiaoqi.security.hrsystem.vo.PanelVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 字段查询类
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebColumnsServiceImpl extends ServiceImpl<WebColumnsMapper,WebColumns> implements IWebColumnsService{


    @Autowired
    WebColumnsMapper webColumnsMapper;

    @Autowired
    UtilMapper utilMapper;
    @Override
    public List<WebColumns> selectByTablename(String tablename) {
        List<WebColumns> webColumnsList = webColumnsMapper.selectByTablename(tablename);
        return webColumnsList;
    }

    @Override
    public Object selectPanelVoByTablename(String tablename,String primaryKey,int k_id,int A0188,String userName) {
        List<PanelVo> panlVofindList = webColumnsMapper.selectPanelVoByTablename(tablename);
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
        Map resultMap = new HashMap<>();
        resultMap.put("groups",groups);
        resultMap.put("hiddenFields",panlVoHandleList);
        resultMap.put("panelInterface",new PanelInterface());
        Map uptademap = new HashMap<>();
        uptademap.put("tableName","k_over");
        uptademap.put("updateCond","K_Id="+k_id);
        resultMap.put("updateTables",uptademap);
        resultMap.put("allowGroup",true);
        resultMap.put("enabled",true);
        return resultMap;
    }
}
