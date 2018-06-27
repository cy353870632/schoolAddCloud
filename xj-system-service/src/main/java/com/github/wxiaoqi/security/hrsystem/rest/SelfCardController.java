package com.github.wxiaoqi.security.hrsystem.rest;

//import com.ace.cache.annotation.Cache;

import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.PanelOpt;
import com.github.wxiaoqi.security.hrsystem.service.IKOverService;
import com.github.wxiaoqi.security.hrsystem.service.IUserService;
import com.github.wxiaoqi.security.hrsystem.service.IWebColumnsService;
import com.github.wxiaoqi.security.hrsystem.service.grid.ICacheService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IHrGridCreatService;
import com.github.wxiaoqi.security.hrsystem.service.panel.IPanelService;
import com.github.wxiaoqi.security.hrsystem.utils.FormatUtil;
import com.github.wxiaoqi.security.hrsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.hrsystem.vo.SelfCardReqVo;
import com.github.wxiaoqi.security.hrsystem.vo.SelfLeaveReqVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/services/app/SelfCard")
@Api(tags = {"请假"},description = "k_leave")
public class SelfCardController extends BaseController{

    @Autowired
    IUserService userService;

    @Autowired
    IPanelService panelService;

    @Autowired
    IKOverService ikOverService;

    @Autowired
    IWebColumnsService webColumnsService;

    @Value("${user.token-header}")
    private String tokenHeader;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    ICacheService cacheService;

    @Autowired
    private IHrGridCreatService hrGridCreatService;

    @RequestMapping(value = "/GetSelfCard", method = RequestMethod.GET)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="添加/编辑", notes="")
    public Object GetSelfOver(HttpServletRequest request,int k_Id) throws Exception{
        String token = request.getHeader(tokenHeader);
//获取a0188
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        String username = claims.get("user_name", String.class);

        Object object = panelService.getPaneByTableNameAndKid("k_card","k_id",k_Id,a0188,username);
        if (object==null){
            return renderError("k_id找不到数据/该表名不存在","data is null");
        }else
        {
            return renderSuccess(object);
        }
    }
    @RequestMapping(value = "/UpdateSelfCard", method = RequestMethod.PUT)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="更新", notes="")
    public Object UpdateSelfOver(HttpServletRequest request, @RequestBody PanelOpt panelOpt) throws Exception{
        String token = request.getHeader(tokenHeader);
//获取a0188
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        if (panelService.updatepanel(panelOpt,a0188)){
            return renderSuccess(true);
        }else
        {
            return renderError("更新失败","时间转换错误/sql拼接错误");
        }
    }

    @RequestMapping(value = "/CreateSelfCard", method = RequestMethod.POST)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="保存", notes="")
    public Object CreateSelfCard(HttpServletRequest request, @RequestBody PanelOpt panelOpt) throws Exception{
        String token = request.getHeader(tokenHeader);
//获取a0188
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        if (panelService.savepanel(panelOpt,a0188)){
            return renderSuccess("保存成功");
        }else
        {
            return renderError("保存失败","时间转换错误/sql拼接错误");
        }
    }


    @RequestMapping(value = "/PostGridBySelfCard", method = RequestMethod.POST)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    public Object PostGridBySelfCard(HttpServletRequest request,@RequestBody SelfCardReqVo input) throws Exception{
        if (input.getCard_Date1() == null || input.getCard_Date1() == null || input.getCard_Date2().equals("") || input.getCard_Date2().equals("")){
            return this.renderError("over_Begin,over_End不能为空",null);
        }
        String token = request.getHeader(tokenHeader);
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);


        String sql = "select * from k_card  where a0188="+a0188+" and card_Date>='" + input.getCard_Date1() + "' and card_Date<='" + input.getCard_Date2() + " 23:59:59'";


        GridOpt gridOpt = new GridOpt();
        gridOpt.setCommandText(sql);
        gridOpt.setGridKey("SelfCard-PostGridBySelfCard");
        gridOpt.setKeyField("k_cara_k_id");

        return this.renderSuccess(hrGridCreatService.creatHrGrid(gridOpt,a0188));
    }

}
