package com.github.wxiaoqi.security.hrsystem.utils;

import com.github.wxiaoqi.security.hrsystem.mapper.UtilMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengyuan
 * @create 2018-05-24 16:53
 * @desc panel中的code处理
 **/
public class StringUtils {

//-------截取指定2个字符之间的字符串
    public static String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }

}
