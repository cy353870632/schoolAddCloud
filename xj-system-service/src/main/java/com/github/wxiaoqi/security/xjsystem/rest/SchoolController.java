package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.School;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.service.*;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.RabbitMqUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.utils.UserMessage;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.SchoolVo;
import com.rabbitmq.client.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/School")
@Api(tags = {""},description = "")
@Slf4j
public class SchoolController extends BaseController{

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
    ISchoolService schoolService;

    @Autowired
    ISysDicService sysDicService;


    @RequestMapping(value = "getAllSchool", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage,int review_status,String schoolstyle_status) throws Exception {
        String id = UserMessage.getUserId();
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") &&!menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }
        if (currentPage==0){
            currentPage =1;
        }
        if (pageSize==0){
            pageSize = 10;
        }
        List<SchoolVo> menuList = schoolService.getAllSchool(keyWord,pageSize,currentPage,review_status,user_code,id,schoolstyle_status);
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal(schoolService.getSchoolTotal(keyWord,review_status,schoolstyle_status));
        return this.renderSuccess(menuList,pageable);
    }

    @RequestMapping(value = "getSchool", method = RequestMethod.POST)
    public Object getSchool(String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }
        School school = schoolService.selectById(id);
        SchoolVo schoolVo = new SchoolVo();
        BeanUtils.copyProperties(school,schoolVo);
        return this.renderSuccess(schoolVo);
    }

    @RequestMapping(value = "addSchool", method = RequestMethod.POST)
    public Object addMenu(@RequestBody School school) throws Exception {
        String id = UserMessage.getUserId();
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }
        try {
            school.setCreat_user(id);
            if (schoolService.addSchool(school)==1)
                return this.renderSuccess();
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级菜单下已经存在该子菜单，请勿重复添加",201);
            if (s2.equals("parentTile_codePath"))
                return this.renderError("该父级菜单下已经存在该跳转路由，请勿重复添加",201);
            else
                return this.renderError("添加失败,请检查填写信息重试",201);
        }

    }


    @RequestMapping(value = "getStyleAndCreatStyle", method = RequestMethod.POST)
    public Object getMenuByid(HttpServletRequest request,String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }
        EntityWrapper<System_dic> wrapper = new EntityWrapper<System_dic>();
        wrapper.eq("dic_name","SCHOOLSTYLE");
        String dic_id = sysDicService.selectMap(wrapper).get("id").toString();
        List<System_dic> style_dic = sysDicService.getChildByParentid(dic_id);
        EntityWrapper<System_dic> wrapper1 = new EntityWrapper<System_dic>();
        wrapper1.eq("dic_name","SCHOOLCREAT");
        dic_id = sysDicService.selectMap(wrapper1).get("id").toString();
        List<System_dic> creatstyle_dic = sysDicService.getChildByParentid(dic_id);
        Map map = new HashMap<>();
        map.put("styleDic",style_dic);
        map.put("creatstyle_dic",creatstyle_dic);
        return this.renderSuccess(map);
    }

    @RequestMapping(value = "upSchool", method = RequestMethod.POST)
    public Object upMenu(@RequestBody School school) throws Exception {
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        String user_id = UserMessage.getUserId();
        if (!menuService.checkMenu(user_role,"schoolMange")){
            return this.renderError("访问权限不够",400);
        }
        School school1 = schoolService.selectById(school.getId());
        if (!school1.getCreat_user().equals(user_id) && !user_code.equals("999")){
            return this.renderError("访问权限不够",400);
        }
        try {
            if (schoolService.upSchool(school,school1)==1)
                return this.renderSuccess();
            else
                return this.renderError("更新失败",201);
        }catch (Exception e){
//            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
                return this.renderError("更新失败,请检查填写信息重试",201);
        }
    }
    @RequestMapping(value = "deleteSchool", method = RequestMethod.POST)
    public Object deleteSchool(String id) throws Exception {
        String user_code = UserMessage.getUserCode();
        if ( !user_code.equals("998") && !user_code.equals("999")){
            return this.renderError("你无权进行该操作",400);
        }
        School school = schoolService.selectById(id);
        if (school!=null) {
            school.setStatus("0");
            schoolService.updateById(school);
            return this.renderSuccess();
        }
        return this.renderError("删除失败",201);
    }

//    @RequestMapping(value = "passSchool", method = RequestMethod.POST)
//    public Object passSchool(HttpServletRequest request,String id) throws Exception {
//        String user_role = UserMessage.getUserRole();
//        String user_code = UserMessage.getUserCode();
//        String user_id = UserMessage.getUserId();
//        if (!user_code.equals("999") && !user_code.equals("998") && !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        if (!user_code.equals("999") && !user_code.equals("998")){
//            return this.renderError("操作权限不够",400);
//        }
//        School school = schoolService.selectById(id);
//        if (school!=null) {
//            school.setReview_status(1);
//            school.setReview_user(user_id);
//            school.setUpdate_date(new Date());
//            schoolService.updateById(school);
//            return this.renderSuccess();
//        }
//        return this.renderError("删除失败",201);
//    }
//    @RequestMapping(value = "nopassSchool", method = RequestMethod.POST)
//    public Object nopassSchool(String id,String reviewFalese) throws Exception {
//        String user_role = UserMessage.getUserRole();
//        String user_code = UserMessage.getUserCode();
//        String user_id = UserMessage.getUserId();
//        if (!user_code.equals("999") && !user_code.equals("998") && !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        if (!user_code.equals("999") && !user_code.equals("998")){
//            return this.renderError("操作权限不够",400);
//        }
//        School school = schoolService.selectById(id);
//        if (school!=null) {
//            school.setReview_status(0);
//            school.setNopass_text(reviewFalese);
//            school.setReview_user(user_id);
//            school.setUpdate_date(new Date());
//            schoolService.updateById(school);
//            return this.renderSuccess();
//        }
//        return this.renderError("操作失败",201);
//    }
//    @RequestMapping(value = "blockMenu", method = RequestMethod.POST)
//    public Object blockMenu(HttpServletRequest request,String id) throws Exception {
//        String token = request.getHeader(tokenHeader);
//        Claims claims = jwtUtil.parseJWT(token);
//        String user_role = claims.get("user_role", String.class);
//        String user_code = claims.get("user_code", String.class);
//        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        Menu menu = new Menu();
//        menu.setId(id);
//        if (menuService.upMenu(menu,2)==1)
//            return this.renderSuccess();
//        else
//            return this.renderError("冻结失败",201);
//    }
//    @RequestMapping(value = "unblockMenu", method = RequestMethod.POST)
//    public Object unblockMenu(HttpServletRequest request,String id) throws Exception {
//        String token = request.getHeader(tokenHeader);
//        Claims claims = jwtUtil.parseJWT(token);
//        String user_role = claims.get("user_role", String.class);
//        String user_code = claims.get("user_code", String.class);
//        if (!user_code.equals("999") || !menuService.checkMenu(user_role,"menuManage")){
//            return this.renderError("访问权限不够",400);
//        }
//        Menu menu = new Menu();
//        menu.setId(id);
//        if (menuService.upMenu(menu,1)==1)
//            return this.renderSuccess();
//        else
//            return this.renderError("生效失败",201);
//    }



    @RequestMapping(value = "passSchool", method = RequestMethod.POST)
    public void passSchool(HttpServletRequest request,String id) throws Exception {
        Connection connection =  RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        String queueName = "queueOne";
        String exchangeName = "exchangerOne";
        String routingKey = "queueOne";
        channel.exchangeDeclare(exchangeName,"direct");
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        int msgCnt =4;
        while(msgCnt-->0){
            String msg = "测试第    "+msgCnt+"    次";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());  //发送消息
            System.out.println("produce msg :"+msg);
            Thread.sleep(1200);
//            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*500));
        }
        channel.close();
        connection.close();
    }

    @RequestMapping(value = "nopassSchool", method = RequestMethod.POST)
    public void nopassSchool(HttpServletRequest request,String id) throws Exception {
        Connection connection =  RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "myExchange";
        channel.exchangeDeclare(exchangeName,"fanout");
        int msgCnt =4;
        while(msgCnt-->0){
            String msg = "测试第    "+msgCnt+"    次";
            channel.basicPublish(exchangeName,"",null,msg.getBytes());  //发送消息
            System.out.println("produce msg :"+msg);
            Thread.sleep(1200);
//            TimeUnit.MILLISECONDS.sleep((long) (Math.random()*500));
        }
        channel.close();
        connection.close();
    }

    @RequestMapping(value = "rabit", method = RequestMethod.POST)
    public void rabit(HttpServletRequest request) throws Exception {

    }
}
