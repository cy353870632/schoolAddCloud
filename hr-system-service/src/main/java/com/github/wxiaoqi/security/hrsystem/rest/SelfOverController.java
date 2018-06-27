package com.github.wxiaoqi.security.hrsystem.rest;

//import com.ace.cache.annotation.Cache;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.auth.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.hrsystem.base.BaseController;
import com.github.wxiaoqi.security.hrsystem.entity.GridOpt;
import com.github.wxiaoqi.security.hrsystem.entity.PanelOpt;
import com.github.wxiaoqi.security.hrsystem.entity.WebColumns;
import com.github.wxiaoqi.security.hrsystem.service.*;
import com.github.wxiaoqi.security.hrsystem.service.grid.ICacheService;
import com.github.wxiaoqi.security.hrsystem.service.grid.IHrGridCreatService;
import com.github.wxiaoqi.security.hrsystem.service.panel.IPanelService;
import com.github.wxiaoqi.security.hrsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.hrsystem.vo.*;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("/api/services/app/SelfOver")
@Api(tags = {"加班"},description = "kover")
public class SelfOverController extends BaseController{

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

    @RequestMapping(value = "/GetSelfOver", method = RequestMethod.GET)
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

        Object object = panelService.getPaneByTableNameAndKid("k_over","k_id",k_Id,a0188,username);
        if (object==null){
            return renderError("k_id找不到数据/该表名不存在","data is null");
        }else
        {
            return renderSuccess(object);
        }
    }
    @RequestMapping(value = "/UpdateSelfOver", method = RequestMethod.PUT)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="更新", notes="")
    public Object UpdateSelfOver(HttpServletRequest request, @RequestBody PanelOpt panelOpt) throws Exception{
        String token = request.getHeader(tokenHeader);
//获取a0188
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        String username = claims.get("user_name", String.class);
        if (panelService.updatepanel(panelOpt,a0188)){
            return renderSuccess(true);
        }else
        {
            return renderError("更新失败","时间转换错误/sql拼接错误");
        }
    }

    @RequestMapping(value = "/CreateSelfOver", method = RequestMethod.POST)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    @ApiOperation(value="保存", notes="")
    public Object SaveSelfOver(HttpServletRequest request, @RequestBody PanelOpt panelOpt) throws Exception{
        String token = request.getHeader(tokenHeader);
//获取a0188
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        String username = claims.get("user_name", String.class);
        if (panelService.savepanel(panelOpt,a0188)){
            return renderSuccess("保存成功");
        }else
        {
            return renderError("保存失败","时间转换错误/sql拼接错误");
        }
    }
    @RequestMapping(value = "/PostGridBySelfOver", method = RequestMethod.POST)
    @ResponseBody
//    @Cache(key = "defaultNormal")
    public Object GetSelfOverGrid(HttpServletRequest request,@RequestBody ReqVo reqVo) throws Exception{
        if (reqVo.getOver_Begin() == null || reqVo.getOver_End() == null || reqVo.getOver_Begin().equals("") || reqVo.getOver_End().equals("")){
            return this.renderError("over_Begin,over_End不能为空",null);
        }
        String token = request.getHeader(tokenHeader);
        token = token.replace("Bearer ","");
        Claims claims = jwtUtil.parseJWT(token);
        Integer a0188 = claims.get("a0188", Integer.class);
        String username = claims.get("user_name", String.class);

//        String sql = "SELECT k.*,a.A0177,r.LEAVE_TYPE,r.work FROM  A01 A INNER JOIN K_LEAVE R ON a.A0188 = A.A0188" +
//                " INNER JOIN K_OVER k ON k.A0188 = A.A0188 WHERE A.A0188=1";
//        String sql = "select A0188,B01.CONTENT, A0190,A0191,A0101,E01.MC0000 from A01,B01,E01 where A01.DEPT_CODE=B01.DEPT_CODE and A01.E0101 = E01.E0101";
//        String sql = "select * from k_card where A0188 = 1 and card_Date >= '2013-01-01' and card_Date <= '2018-06-06 23:59:59'";
//        String sql = "select ZPReqPlan.ZPReqPlanID,E01.E0101,b01.B0110,e01.BM0000, ZPReqItem.*,ZPPlan.Caption,ZPReq.Caption,b01.dept_code,b01.Content from E01 inner join ZPReqItem on E01.E0101=ZPReqItem.E0101 inner join b01 on e01.dept_code = b01.dept_code inner join bm_gw on bm_gw.BM0000=e01.BM0000 " +
//                "inner join ZPReqPlan on ZPReqItem.ZPReqItem01 = ZPReqPlan.ZPReqItem01 inner join ZPPlan on ZPReqPlan.PlanID=ZPPlan.PlanID inner join ZPReq on ZPReqItem.ReqID=ZPReq.ReqID where ZPReqItem.Status=7 and ZPPlan.Status=1";
       String sql = "select * from k_over where A0188 = "+a0188+" and over_begin >= '"+reqVo.getOver_Begin()+"' and Over_End <= '"+reqVo.getOver_End().substring(0,10)+" 23:59:59'";

        GridOpt gridOpt = new GridOpt();
        gridOpt.setCommandText(sql);
        gridOpt.setGridKey("SELFOVER_GETSELFOVERGRID");
        gridOpt.setKeyField("K_OVER_K_ID");
        return this.renderSuccess(hrGridCreatService.creatHrGrid(gridOpt,a0188));
    }

}
