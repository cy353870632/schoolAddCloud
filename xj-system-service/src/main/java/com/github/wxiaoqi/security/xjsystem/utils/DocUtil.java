package com.github.wxiaoqi.security.xjsystem.utils;

import com.github.wxiaoqi.security.xjsystem.service.ISysDocService;
import com.github.wxiaoqi.security.xjsystem.service.impl.SysDocServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chengyuan
 * @create 2018-05-24 16:53
 **/
@Component
public class DocUtil {
    @Autowired
    private ISysDocService sysDocService;

    private static DocUtil docUtil;

    @PostConstruct
    public void init() {
        docUtil = this;
        docUtil.sysDocService = this.sysDocService;
    }
    public static void saveDoc(String active,String level) throws Exception{
        docUtil.sysDocService.addSysDoc(active,level);
    }


}
