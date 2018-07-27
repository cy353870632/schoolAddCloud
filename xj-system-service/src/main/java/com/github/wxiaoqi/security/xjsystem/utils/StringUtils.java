package com.github.wxiaoqi.security.xjsystem.utils;

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
    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param
     * @param
     * @param
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.substring(strStart.length()+strStartIndex).indexOf(strEnd)+strStart.length()+strStartIndex;

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex+strStart.length(),strEndIndex);
        return result;
    }

}
