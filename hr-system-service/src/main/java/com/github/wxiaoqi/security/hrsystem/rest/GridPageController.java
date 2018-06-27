package com.github.wxiaoqi.security.hrsystem.rest;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import com.github.wxiaoqi.security.hrsystem.service.IWebModuleInfoService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IHrGridCreatService;
import com.github.wxiaoqi.security.hrsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.hrsystem.vo.DefaultVo;
import com.github.wxiaoqi.security.hrsystem.vo.ReqVo;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/services/app/Grid")
@Api(tags = {"分页"},description = "hr")
public class GridPageController extends BaseController{

    @Autowired
    IHrGridCreatService hrGridCreatService;

    @Autowired
    JWTUtil jwtUtil;

    @Value("${user.token-header}")
    private String tokenHeader;

    @RequestMapping(value = "/GetPageData", method = RequestMethod.GET)
    public Object getPageData(HttpServletRequest request,String gridKey,Integer pageSize,Integer currentPage) throws Exception{
        if (pageSize == null|| currentPage == null || pageSize == 0||currentPage == 0){
            pageSize = 10;
            currentPage = 1;
        }
        String token = request.getHeader(tokenHeader);
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        try {
            return this.renderSuccess(hrGridCreatService.getPageData(gridKey,a0188,pageSize,currentPage));

        }catch (Exception e){
            return this.renderError("无缓存数据",e.getMessage());
        }
    }

}
