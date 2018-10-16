package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.User;
import com.github.wxiaoqi.security.xjsystem.mapper.RoleMapper;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.*;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.UserInfoVo;
import com.github.wxiaoqi.security.xjsystem.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/User")
@Api(tags = {""},description = "")
@Slf4j
public class UserController extends BaseController{

    @Autowired
    IUserService userService;

    @Value("${user.token-header}")
    private String tokenHeader;

    //token过期时间
    @Value("${jwt.expire}")
    private long ttlMillis;

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    ICacheService cacheService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IMenuService menuService;

    @Autowired
    RoleMapper roleMapper;


    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    public Object getMenu(HttpServletRequest request) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        UserInfoVo userInfoVo = userService.getInfo(claims);
        Map restltMap = new HashMap<>();
        restltMap.put("userInfo",userInfoVo);
        List<Menu> menuList = menuService.getMenu(user_role);
        if (menuList!=null && menuList.size()>0){
            restltMap.put("menus",menuService.getMenu(user_role));
            DocUtil.saveDoc("获取用户信息成功", "普通");
            return this.renderSuccess(restltMap);
        }else
        {
            return this.renderError("您的权限没有可用菜单，请联系管理员",201);
        }
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public Object getUserInfo(HttpServletRequest request) throws Exception {
        String id = UserMessage.getUserId();
        User user = userService.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        if (user==null){
            return this.renderError("无该用户信息",404);
        }
        return this.renderSuccess(userVo);
    }

    //推广员列表
    @RequestMapping(value = "getPromoterList", method = RequestMethod.POST)
    public Object getPromoterList(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        Boolean status = false;
        String id = UserMessage.getUserId();
        String user_role = UserMessage.getUserRole();
        if (menuService.checkMenu(user_role,"promoterMange")){
            if (pageSize == null){
                pageSize = 10;
            }
            if (currentPage == null){
                currentPage = 1;
            }
            List<User> promoterList = userService.getPromoter(id,keyWord,pageSize,currentPage);
            Integer total = userService.getPromoterTotal(id,keyWord);
            Pageable pageable = new Pageable();
            pageable.setCurrentPage(currentPage);
            pageable.setPageSize(pageSize);
            pageable.setTotal(total);
            DocUtil.saveDoc("获取推广员信息成功", "普通");
            return this.renderSuccess(promoterList,pageable);
        }
        return this.renderError("您没有访问该页面的权限",400);//权限不够
    }

    @RequestMapping(value = "addPromoter", method = RequestMethod.POST)
    public Object addPromoter(HttpServletRequest request,@RequestBody User user) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        try {
            if (userService.addPromoter(user)==1) {
                DocUtil.saveDoc("新增推广员信息成功", "普通");
                return this.renderSuccess();
            }
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("phone"))
                return this.renderError("手机号 "+s+" 已经存在，请勿重复添加",201);
            if (s2.equals("l_name"))
                return this.renderError("用户名 "+s+"已经存在，请勿重复添加",201);
            if (s2.equals("id_card"))
                return this.renderError("身份证号 "+s+"已经存在，请勿重复添加",201);
            else
                return this.renderError("保存失败,请检查填写信息重试",201);
        }

    }

    @RequestMapping(value = "deletePromoter", method = RequestMethod.POST)
    public Object deletePromoter(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        int status = userService.deletePromoter(uid,user_code);
        if (status==1)
            return this.renderSuccess();
        else if(status==3)
            return this.renderError("数据不存在",404);
        else {
            return this.renderError("删除出错/该数据不允许被删除",201);//201只读数据或者过程报错
        }
    }

    @RequestMapping(value = "getPromoter", method = RequestMethod.POST)
    public Object getPromoter(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        User user = userService.selectById(uid);
        if (user!=null)
            return this.renderSuccess(user);
        else if(user==null)
            return this.renderError("该用户不存在",404);
        else {
            return this.renderError("系统错误",201);//201只读数据或者过程报错
        }
    }
    @RequestMapping(value = "upPromoter", method = RequestMethod.POST)
    public Object upPromoter(HttpServletRequest request,@RequestBody User user) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        try {
            int status = userService.updatePromoter(user,user_code);
            if (status==1) {
                DocUtil.saveDoc("更新推广员信息成功", "敏感");
                return this.renderSuccess();
            }
            else if(status==3)
                return this.renderError("数据不存在",404);
            else {
                return this.renderError("更新出错/该数据不允许被编辑",201);//201只读数据或者过程报错
            }
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("phone"))
                return this.renderError("手机号 "+s+" 已经存在",201);
            if (s2.equals("l_name"))
                return this.renderError("用户名 "+s+"已经存在",201);
            if (s2.equals("id_card"))
                return this.renderError("身份证号 "+s+"已经存在",201);
            else
                return this.renderError("更新失败,请检查填写信息重试",201);
        }
    }
//重置密码
    @RequestMapping(value = "restPWD", method = RequestMethod.POST)
    public Object restPWD(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        int status = userService.restPwd(uid,user_code);
        if (status==1) {
            DocUtil.saveDoc("修改密码", "敏感");
            return this.renderSuccess();
        }
        else if(status==3)
            return this.renderError("数据不存在",404);
        else {
            return this.renderError("重置出错/该数据不允许被编辑",201);//201只读数据或者过程报错
        }
    }

    //-------------系统管理员设置-----------------
    @RequestMapping(value = "getManageUserList", method = RequestMethod.POST)
    public Object getManageUserList(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        Boolean status = false;
        String id = UserMessage.getUserId();
        String user_code = UserMessage.getUserCode();
        String user_role = UserMessage.getUserRole();
        if (menuService.checkMenu(user_role,"userMange")){
            if (pageSize == null){
                pageSize = 10;
            }
            if (currentPage == null){
                currentPage = 1;
            }
            List<User> promoterList = userService.getManageUser(id,keyWord,pageSize,currentPage);
            Integer total = userService.getManageUserTotal(id,keyWord);
            Pageable pageable = new Pageable();
            pageable.setCurrentPage(currentPage);
            pageable.setPageSize(pageSize);
            pageable.setTotal(total);
            DocUtil.saveDoc("获取管理员信息成功", "普通");
            return this.renderSuccess(promoterList,pageable);
        }
        return this.renderError("您没有访问该页面的权限",400);//权限不够
    }

    @RequestMapping(value = "addManageUser", method = RequestMethod.POST)
    public Object addManageUser(HttpServletRequest request,@RequestBody User user) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        try {
            if (userService.addManageUser(user)==1) {
                DocUtil.saveDoc("新增管理员信息成功", "普通");
                return this.renderSuccess();
            }
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("phone"))
                return this.renderError("手机号 "+s+" 已经存在，请勿重复添加",201);
            if (s2.equals("l_name"))
                return this.renderError("用户名 "+s+"已经存在，请勿重复添加",201);
            if (s2.equals("id_card"))
                return this.renderError("身份证号 "+s+"已经存在，请勿重复添加",201);
            else
                return this.renderError("保存失败,请检查填写信息重试",201);
        }

    }

    @RequestMapping(value = "deleteManageUser", method = RequestMethod.POST)
    public Object deleteManageUser(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        int status = userService.deleteManageUser(uid,user_code);
        if (status==1){
            DocUtil.saveDoc("删除管理员信息成功", "敏感");
            return this.renderSuccess();}
        else if(status==3)
            return this.renderError("数据不存在",404);
        else {
            return this.renderError("删除出错/该数据不允许被删除",201);//201只读数据或者过程报错
        }
    }

    @RequestMapping(value = "getManageUser", method = RequestMethod.POST)
    public Object getManageUser(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        DocUtil.saveDoc("获取管理员信息", "普通");
        User user = userService.selectById(uid);
        if (user!=null)
            return this.renderSuccess(user);
        else if(user==null)
            return this.renderError("该用户不存在",404);
        else {
            return this.renderError("系统错误",201);//201只读数据或者过程报错
        }
    }
    @RequestMapping(value = "upManageUser", method = RequestMethod.POST)
    public Object upManageUser(HttpServletRequest request,@RequestBody User user) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        try {
            int status = userService.updateManageUser(user,user_code);
            if (status==1) {
                DocUtil.saveDoc("更新管理员信息", "敏感");
                return this.renderSuccess();
            }
            else if(status==3)
                return this.renderError("数据不存在",404);
            else {
                return this.renderError("更新出错/该数据不允许被编辑",201);//201只读数据或者过程报错
            }
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("phone"))
                return this.renderError("手机号 "+s+" 已经存在",201);
            if (s2.equals("l_name"))
                return this.renderError("用户名 "+s+"已经存在",201);
            if (s2.equals("id_card"))
                return this.renderError("身份证号 "+s+"已经存在",201);
            else
                return this.renderError("更新失败,请检查填写信息重试",201);
        }

    }

    @RequestMapping(value = "restManageUserPWD", method = RequestMethod.POST)
    public Object restManageUserPWD(HttpServletRequest request,String uid) throws Exception{
        String user_code = UserMessage.getUserCode();
        if (!user_code.equals("999") && !user_code.equals("998")){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        int status = userService.restPwd(uid,user_code);
        if (status==1) {
            DocUtil.saveDoc("修改管理员密码成功", "敏感");
            return this.renderSuccess();
        }
        else if(status==3)
            return this.renderError("数据不存在",404);
        else {
            return this.renderError("重置出错/该数据不允许被编辑",201);//201只读数据或者过程报错
        }
    }

    @RequestMapping(value = "changPwd", method = RequestMethod.POST)
    public Object changPwd(HttpServletRequest request,String id,String oldpass,String pass,String loginToken) throws Exception{
        if (pass.length()<6){
            return this.renderError("密码长度不符，至少为6位",400);//权限不够
        }
//        String token = "";
//        if (loginToken == null || loginToken.equals(""))
//            token = request.getHeader(tokenHeader);
//        else
//            token = loginToken;
//        if (token==null||token.equals("")){
//            return this.renderError("您没有权限进行该操作",400);//权限不够
//        }
        String user_id = UserMessage.getUserId();
        if (!user_id.equals(id)){
            return this.renderError("您没有权限进行该操作",400);//权限不够
        }
        DocUtil.saveDoc("修改密码成功", "敏感");
        Integer status = userService.changPwd(id,oldpass,pass);
        if (status==1){
            return this.renderSuccess("更新成功");
        }else if (status==3){
            return this.renderError("原密码不正确，请输入正确的原密码后重试",400);//权限不够
        }else
            return this.renderError("更新出错/该数据不允许被编辑",201);//201只读数据或者过程报错
    }



}
