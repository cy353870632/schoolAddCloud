package com.github.wxiaoqi.security.xjsystem.rest;

//import com.ace.cache.annotation.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @author chengyuan
 * @create 2018-05-22 11:51
 */
@RestController
@RequestMapping("hrsystem/hrsystem")
@Api(tags = {"系统模块"},description = "hr")
public class HrSystemController{

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
//            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
//    })

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    @ResponseBody
//    @Cacheable(key = "#name")
    @ApiOperation(value="测试swagger2", notes="")
    public Object getUserInfo(String name) throws Exception {
//        Map map = webParamsMapper.selectAllClowms(name);
//        List<WebFuncFrame> webFuncFrame = webFuncFrameService.selectBymodid(11,"11");
       return "连接成功："+name;
    }

}
