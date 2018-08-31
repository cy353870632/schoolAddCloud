package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.entity.System_doc;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 * 系统操作日志
 */
@RestController
@RequestMapping("/api/SysDoc")
@Api(tags = {""},description = "")
@Slf4j
public class SysDocController extends BaseController{

    @Autowired
    IUserService userService;

    @Value("${user.token-header}")
    private String tokenHeader;

    //token过期时间
    @Value("${jwt.expire}")
    private long ttlMillis;

    @Autowired
    ICacheService cacheService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IMenuService menuService;

    @Autowired
    ISysDocService sysDocService;

    @Autowired
    ISysDicService sysDicService;


    @RequestMapping(value = "getAllDoc", method = RequestMethod.POST)
    public Object getAllDoc(String keyWord,String startDoTime,String endDoTime,Integer pageSize,Integer currentPage) throws Exception {
        String id = UserMessage.getUserId();
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") &&!menuService.checkMenu(user_role,"sysdoDoc")){
            return this.renderError("访问权限不够",400);
        }
        if (currentPage==0){
            currentPage =1;
        }
        if (pageSize==0){
            pageSize = 10;
        }
        Date startDate = null;
        Date endDate = null;
        if (startDoTime!=null&&!startDoTime.equals("")){
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            startDoTime = startDoTime.replace("Z", " UTC");//UTC是本地时间
            startDate = s.parse(startDoTime);
        }
        if (endDoTime!=null&&!endDoTime.equals("")){
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            endDoTime = endDoTime.replace("Z", " UTC");//UTC是本地时间
            endDate = s.parse(endDoTime);
        }
        List<System_doc> menuList = sysDocService.getAllSysDoc(keyWord,startDate,endDate,pageSize,currentPage);
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal(sysDocService.getSysDocTotal(keyWord,startDate,endDate));
        return this.renderSuccess(menuList,pageable);
    }

//    @RequestMapping(value = "addSchool", method = RequestMethod.POST)
//    public Object addMenu(@RequestBody School school) throws Exception {
//        String id = UserMessage.getUserId();
//        String user_code = UserMessage.getUserCode();
//        String user_role = UserMessage.getUserRole();
//        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysdoDoc")){
//            return this.renderError("访问权限不够",400);
//        }
//        try {
//            school.setCreat_user(id);
//            if (schoolService.addSchool(school)==1)
//                return this.renderSuccess();
//            else
//                return this.renderError("保存失败",201);
//        }catch (Exception e){
//            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
//            if (s2.equals("parentTitle_title"))
//                return this.renderError("该父级菜单下已经存在该子菜单，请勿重复添加",201);
//            if (s2.equals("parentTile_codePath"))
//                return this.renderError("该父级菜单下已经存在该跳转路由，请勿重复添加",201);
//            else
//                return this.renderError("添加失败,请检查填写信息重试",201);
//        }
//
//    }



}
