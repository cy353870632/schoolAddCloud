package com.github.wxiaoqi.security.xjsystem.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormatUtil {

	public final static String AppendFormat(String sql,String ...parms) {
		for (int i =0;i < parms.length;i++){
			String parm = parms[i];
			if (sql.contains("{"+i+"}")){
				sql = sql.replaceAll("\\{"+i+"\\}",parm);
			}
		}
		return sql;
	}
}
