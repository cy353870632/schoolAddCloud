package com.github.wxiaoqi.security.hrsystem.rest;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.mapper.WebParamsMapper;
import com.github.wxiaoqi.security.hrsystem.service.IRyQuerySuitesService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import com.github.wxiaoqi.security.hrsystem.service.IWebReportsService;
import com.github.wxiaoqi.security.hrsystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/services/app/WorkDesktop")
@Api(tags = {"桌面模块"},description = "WorkDesktop")
public class WorkDesktopController extends BaseController {

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
//            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
//    })
    @Value("${user.token-header}")
    private String tokenHeader;

    @Autowired
    WebParamsMapper webParamsMapper;

    @Autowired
    IRyQuerySuitesService ryQuerySuitesService;

    @Autowired
    IWebReportsService webReportsService;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/GetDeskQueryInfo", method = RequestMethod.GET)
    @ResponseBody
//    @Cacheable(key = "#name")
    @ApiOperation(value="常用查询", notes="")
    public Object GetDeskQueryInfo() throws Exception {
        String token = request.getHeader(tokenHeader);
        Integer a0188;
        try {
            token = token.replace("Bearer ","");
            Claims claims = jwtUtil.parseJWT(token);
            a0188 = claims.get("a0188", Integer.class);
            String username = claims.get("user_name", String.class);
        }catch (Exception e){
            return renderError("用户未登陆",e.getMessage());
        }
        List result = ryQuerySuitesService.getRyQuerySuitesBystrDetail(a0188);
        Map map = new HashMap<>();
        if (result==null || result.size()<1){
            return renderSuccess(map);
        }else
        {
            map.put("title","常用查询");
            map.put("deskQuerys",result);
            map.put("hasMore",true);
            return renderSuccess(map);
        }
    }

    @RequestMapping(value = "/GetDeskReportInfo", method = RequestMethod.GET)
    @ResponseBody
//    @Cacheable(key = "#name")
    @ApiOperation(value="常用报表", notes="")
    public Object GetDeskReportInfo() throws Exception {
        String token = request.getHeader(tokenHeader);
        Integer a0188;
        try {
            token = token.replace("Bearer ","");
            Claims claims = jwtUtil.parseJWT(token);
            a0188 = claims.get("a0188", Integer.class);
            String username = claims.get("user_name", String.class);
        }catch (Exception e){
            return renderError("用户未登陆",e.getMessage());
        }
        List result = webReportsService.getWebReportsBystrDetail(a0188);
        Map map = new HashMap<>();
        if (result==null || result.size()<1){
            return renderSuccess(map);
        }else
        {
            map.put("title","常用报表");
            map.put("deskReport",result);
            map.put("hasMore",true);
            return renderSuccess(map);
        }
    }

}
