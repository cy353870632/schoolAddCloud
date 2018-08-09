package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.xjsystem.base.BaseController;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.entity.System_dic;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.service.IMenuService;
import com.github.wxiaoqi.security.xjsystem.service.ISysDicService;
import com.github.wxiaoqi.security.xjsystem.service.IUserService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.utils.StringUtils;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import com.github.wxiaoqi.security.xjsystem.vo.SysDicVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/SysDic")
@Api(tags = {""},description = "")
@Slf4j
public class SysDicController extends BaseController{

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
    ISysDicService sysDicService;

    @RequestMapping(value = "getAllSysDic", method = RequestMethod.POST)
    public Object getAllMenu(HttpServletRequest request,String keyWord,Integer pageSize,Integer currentPage) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.get("id", String.class);
        String user_code = claims.get("user_code", String.class);
        String user_role = claims.get("user_role", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        if (currentPage==0){
            currentPage =1;
        }
        if (pageSize==0){
            pageSize = 10;
        }
        List<SysDicVo> sysDicVos = sysDicService.getAllSysDic(keyWord,pageSize,currentPage);
        List<SysDicVo> sysDicVosarent =  sysDicService.getAllParent();
        Map result = new HashMap<>();
        result.put("parentList",sysDicVosarent);
        result.put("dataList",sysDicVos);

        for (SysDicVo sysDicVo:sysDicVos){
            sysDicVo.setChildren(sysDicService.getChildByParentid(sysDicVo.getId()));
        }
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        pageable.setCurrentPage(currentPage);
        pageable.setTotal(sysDicService.getSysDicTotal(keyWord));
        return this.renderSuccess(result,pageable);
    }

    @RequestMapping(value = "addSysDic", method = RequestMethod.POST)
    public Object addSysDic(HttpServletRequest request,@RequestBody System_dic system_dic) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String id = claims.get("id", String.class);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        if (system_dic.getDic_name()==null || (system_dic.getEnd_mark().equals("0")&&system_dic.getDic_name().equals(""))){
            return this.renderError("字典编码不允许为空",400);
        }
        try {
            if (sysDicService.addSysDic(system_dic)==1) {
                sysDicService.cacheClear(system_dic.getParent_id());
                return this.renderSuccess();
            }
            else
                return this.renderError("保存失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级字典下已经存在该字典名称，请勿重复添加",201);
//            if (s2.equals("parentTile_codePath"))
//                return this.renderError("该父级菜单下已经存在该跳转路由，请勿重复添加",201);
            else
                return this.renderError("添加失败,请检查填写信息重试",201);
        }

    }


    @RequestMapping(value = "getSysDic", method = RequestMethod.POST)
    public Object getMenuByid(HttpServletRequest request,String id) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        System_dic system_dic = sysDicService.selectById(id);
        System_dic system_dic1 = new System_dic();
        Map resultMap = new HashMap<>();

        if (system_dic.getParent_id().equals("0") && system_dic.getEnd_mark().equals("0")){
            resultMap.put("parent","0");
        }
        else {
            system_dic1 = sysDicService.selectById(system_dic.getParent_id());
            resultMap.put("parent",system_dic1);

        }
        resultMap.put("data",system_dic);
        return this.renderSuccess(resultMap);
    }

    @RequestMapping(value = "upSysDic", method = RequestMethod.POST)
    public Object upMenu(HttpServletRequest request,@RequestBody System_dic system_dic) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        try {
            String oldParentID = sysDicService.selectById(system_dic.getId()).getParent_id();

            if (sysDicService.upSysDic(system_dic,1)==1) {
                sysDicService.cacheClear(system_dic.getParent_id());
                if (!oldParentID.equals(system_dic.getParent_id())){
                    sysDicService.cacheClear(oldParentID);
                }
                return this.renderSuccess();
            }
            else
                return this.renderError("更新失败",201);
        }catch (Exception e){
            String s1 = e.getCause().getMessage();
            String s = StringUtils.subString(e.getCause().getMessage(),"entry '","' for");
            String s2 = StringUtils.subString(e.getCause().getMessage(),"for key '","'");
            if (s2.equals("parentTitle_title"))
                return this.renderError("该父级字典下已经存在该字典名称，请勿重复添加",201);
//            if (s2.equals("parentTile_codePath"))
//                return this.renderError("该父级菜单下已经存在该跳转路由，请勿重复添加",201);
            else
                return this.renderError("更新失败,请检查填写信息重试",201);
        }
    }
    @RequestMapping(value = "deleteSysDic", method = RequestMethod.POST)
    public Object deleteMenu(HttpServletRequest request,String id) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        System_dic system_dic = sysDicService.selectById(id);
        if (sysDicService.upSysDic(system_dic,0)==1) {
            sysDicService.cacheClear(system_dic.getParent_id());
            return this.renderSuccess();
        }
        else
            return this.renderError("删除失败",201);
    }
    @RequestMapping(value = "blockSysDic", method = RequestMethod.POST)
    public Object blockMenu(HttpServletRequest request,String id) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        System_dic system_dic = sysDicService.selectById(id);
        if (sysDicService.upSysDic(system_dic,2)==1)
        {
            sysDicService.cacheClear(system_dic.getParent_id());
            return this.renderSuccess();
        }
        else
            return this.renderError("冻结失败",201);
    }
    @RequestMapping(value = "unblockSysDic", method = RequestMethod.POST)
    public Object unblockMenu(HttpServletRequest request,String id) throws Exception {
        String token = request.getHeader(tokenHeader);
        Claims claims = jwtUtil.parseJWT(token);
        String user_role = claims.get("user_role", String.class);
        String user_code = claims.get("user_code", String.class);
        if (!user_code.equals("999") && !menuService.checkMenu(user_role,"sysDicManage")){
            return this.renderError("访问权限不够",400);
        }
        System_dic system_dic = sysDicService.selectById(id);
        if (sysDicService.upSysDic(system_dic,1)==1)
        {
            sysDicService.cacheClear(system_dic.getParent_id());
            return this.renderSuccess();
        }
        else
            return this.renderError("生效失败",201);
    }
}
