package com.github.wxiaoqi.security.xjsystem.base;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.wxiaoqi.security.xjsystem.vo.Pageable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description：基础 controller
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public abstract class BaseController {
    // 控制器本来就是单例，这样似乎更加合理
    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        /**
         * 防止XSS攻击
         */
    }


//    /**
//     * 获取当前登录用户id
//     *
//     * @return {Long}
//     */
//    public Long getUserId() {
//        return this.getShiroUser().getId();
//    }
//
//    /**
//     * 获取当前登录用户名
//     *
//     * @return {String}
//     */
//    public String getStaffName() {
//        return this.getShiroUser().getName();
//    }

    /**
     * ajax失败
     *
     * @param msg 失败的消息 errExp 系统报错信息
     * @return {Object}
     */
    public Object renderError(String msg,String errExp) {
        Map result = new HashMap<>();
        Map error = new HashMap<>();
        error.put("code",0);
        error.put("message",msg);
        error.put("details",errExp);
        error.put("validationErrors",null);
        result.put("success",false);
        result.put("error",error);
        return result;
    }

    /**
     * ajax成功
     *
     * @return {Object}
     */
    public Object renderSuccess() {
        Map result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    /**
     * ajax成功
     *
     * @param msg 消息
     * @return {Object}
     */
//    public Object renderSuccess(String msg) {
//        Map result = new HashMap<>();
//        result.put("success",true);
//        result.put("msg",msg);
//        return result;
//    }

    /**
     * ajax成功
     *
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj, Pageable pageable) {
        Map result = new HashMap<>();
        result.put("success",true);
        result.put("result",obj);
        result.put("pageable",pageable);
        return result;
    }
    /**
     * ajax成功
     *
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Map result = new HashMap<>();
        result.put("success",true);
        result.put("result",obj);
        return result;
    }
    /**
     * ajax成功
     *
     * @param obj 成功时的对象 obj1 准考证状态
     * @return {Object}
     */
    public Object renderTicketSuccess(Object obj,String obj1) {
        Map result = new HashMap<>();
        result.put("success",true);
        result.put("result",obj);
        result.put("result1",obj1);
        return result;
    }

    public <T> Page<T> getPage(int current, int size, String sort, String order) {
        Page<T> page = new Page<T>(current, size, sort);
        if ("desc".equals(order)) {
            page.setAsc(false);
        } else {
            page.setAsc(true);
        }
        return page;
    }

//    public <T> PageInfo pageToPageInfo(Page<T> page) {
//        PageInfo pageInfo = new PageInfo();
//        pageInfo.setRows(page.getRecords());
//        pageInfo.setTotal(page.getTotal());
//        pageInfo.setOrder(page.getOrderByField());
//        return pageInfo;
//    }


    /**
     * redirect跳转
     *
     * @param url 目标url
     */
    protected String redirect(String url) {
        return new StringBuilder("redirect:").append(url).toString();
    }

//    /**
//     * 下载文件
//     *
//     * @param file 文件
//     */
//    protected ResponseEntity<Resource> download(File file) {
//        String fileName = file.getName();
//        return download(file, fileName);
//    }

//    /**
//     * 下载
//     *
//     * @param file     文件
//     * @param fileName 生成的文件名
//     * @return {ResponseEntity}
//     */
//    protected ResponseEntity<Resource> download(File file, String fileName) {
//        return download(file, fileName, MediaType.APPLICATION_OCTET_STREAM);
//    }

//    /**
//     * 下载
//     *
//     * @param file      文件
//     * @param fileName  生成的文件名
//     * @param mediaType 文件类型
//     * @return {ResponseEntity}
//     */
//    protected ResponseEntity<Resource> download(File file, String fileName, MediaType mediaType) {
//        Resource resource = new FileSystemResource(file);
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes()).getRequest();
//        String header = request.getHeader("User-Agent");
//        // 避免空指针
//        header = header == null ? "" : header.toUpperCase();
//        HttpStatus status;
//        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
//            fileName = URLUtils.encodeURL(fileName, Charsets.UTF_8);
//            status = HttpStatus.OK;
//        } else {
//            fileName = new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
//            status = HttpStatus.CREATED;
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(mediaType);
//        headers.setContentDispositionFormData("attachment", fileName);
//        return new ResponseEntity<Resource>(resource, headers, status);
//    }
}
