package com.github.wxiaoqi.security.hrsystem.rest;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebFuncFrameService;
import com.github.wxiaoqi.security.hrsystem.service.IWebModuleInfoService;
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
@RequestMapping("/api/services/app/Default")
@Api(tags = {"桌面"},description = "hr")
public class defaultController extends BaseController{

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
//            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
//    })

    @Autowired
    IUserService userService;

    @Autowired
    IWebModuleInfoService webModuleInfoService;

    @Autowired
    IWebFuncFrameService webFuncFrameService;

    @Value("${user.token-header}")
    private String tokenHeader;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/GetDefaultInfo", method = RequestMethod.GET)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="默认桌面", notes="")
    public Object getDefaultInfo(HttpServletRequest request, Integer A0188,@RequestParam(value="mode", required=false)String mode){
        try {
            if (A0188 == null){
                return renderError("A0188不能为空",null);
            }
            String token = request.getHeader(tokenHeader);
//            IJWTInfo ijwtInfo = userAuthUtil.getInfoFromToken(token);
//            Integer a0188 = Integer.valueOf( ijwtInfo.getId()).intValue();
            UserInfoVo userInfoVo = userService.getUserInfoVo(A0188);
            List webModuleInfoList = webModuleInfoService.selectModeAndFramByA0188(A0188);
            Map resultMap = new HashMap<>();
            resultMap.put("userInfo",userInfoVo);
            resultMap.put("funcMenu",webModuleInfoList);
            return this.renderSuccess(resultMap);
        }catch (Exception e){
            return this.renderError("获取默认桌面失败",e.getMessage());
        }

    }

    @RequestMapping(value = "/GetFuncMenuItems", method = RequestMethod.GET)
    @ResponseBody
//    @Cache(key = "defaultNormal{1}")
    @ApiOperation(value="按modeid获取模块funframe", notes="")
    public Object getDefaultInfoBymodeid(ReqVo reqVo, HttpServletRequest request){
        try {
            String token = request.getHeader(tokenHeader);
            token = token.replace("Bearer ","");
            Claims claims = jwtUtil.parseJWT(token);
            Integer a0188 = claims.get("a0188", Integer.class);
            List<DefaultVo> resultList = webFuncFrameService.selectBymodid(a0188,reqVo.getMId());
            return this.renderSuccess(resultList);
        }catch (Exception e){
            return this.renderError("获取失败",e.getMessage());
        }

    }

}
