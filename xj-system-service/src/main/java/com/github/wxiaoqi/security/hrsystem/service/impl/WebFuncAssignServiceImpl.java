package com.github.wxiaoqi.security.hrsystem.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncAssign;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.mapper.WebFuncAssignMapper;
import com.github.wxiaoqi.security.hrsystem.mapper.WebFuncFrameMapper;
import com.github.wxiaoqi.security.hrsystem.service.IWebActorAssignService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncAssignService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengyuan
 * @create 2018-05-23 8:48
 * @desc 菜单fram和权限关联表
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class WebFuncAssignServiceImpl extends ServiceImpl<WebFuncAssignMapper,WebFuncAssign> implements IWebFuncAssignService{


    @Autowired
    WebFuncAssignMapper webFuncAssignMapper;

    @Autowired
    IWebActorAssignService webActorAssignService;


    @Override
    public List<String> selectFunccodeByAcotrid(Integer A0188) {
        List<Integer> actoridList = webActorAssignService.selectActoridByA0188(A0188);
        if (actoridList.size()>0){
            List<String> webFuncAssignList = webFuncAssignMapper.selectFunccodeByActorid(actoridList);
            return this.removeDuplicate(webFuncAssignList);
        }
        return null;
    }

    public   List  removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
