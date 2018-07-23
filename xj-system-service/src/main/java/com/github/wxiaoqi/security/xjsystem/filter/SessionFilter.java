package com.github.wxiaoqi.security.xjsystem.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.utils.JWTUtil;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

//全局过滤
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
@Slf4j
public class SessionFilter implements Filter {

    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";

    //不需要登录就可以访问的路径(比如:注册登录等)
    @Value("${gate.ignore.startWith}")
    String includeUrls;

    @Value("${user.token-header}")
    private String tokenHeader;

    @Autowired
    ICacheService cacheService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        System.out.println("filter url:"+uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);

        if (!needFilter||uri.equals("/api/User/changPwd")) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            String token = request.getHeader(tokenHeader);
            String method = request.getMethod();
            token = token.replace("Bearer ", "");
            JWTUtil jwtUtil = new JWTUtil();
            PrintWriter out = null;
            String user_role = "";
            if (!method.equals("OPTIONS")) {
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    user_role =  claims.get("user_role", String.class);
                    if (claims.getId() == null) {
                        JSONObject map = new JSONObject();
                        map.put("message", "用户Token过期异常");
                        map.put("status", 401);
                        out = response.getWriter();
                        out.append(map.toString());
                        return;
                    }
                } catch (Exception e) {
                    JSONObject map = new JSONObject();
                    map.put("message", "用户Token过期异常");
                    map.put("status", 401);
                    out = response.getWriter();
                    out.append(map.toString());
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    /**
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     * @param uri
     */
    public boolean isNeedFilter(String uri) {
        boolean flag = false;
        for (String s : includeUrls.split(",")) {
            if (uri.startsWith(s)) {
                return flag;
            }
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
