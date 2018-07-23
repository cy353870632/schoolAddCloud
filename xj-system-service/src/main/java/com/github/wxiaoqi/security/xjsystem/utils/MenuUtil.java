package com.github.wxiaoqi.security.xjsystem.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.wxiaoqi.security.xjsystem.entity.Menu;
import com.github.wxiaoqi.security.xjsystem.service.ICacheService;
import com.github.wxiaoqi.security.xjsystem.vo.MenuVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jdk.internal.util.xml.BasicXmlPropertiesProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MenuUtil {

	@Autowired
	ICacheService cacheService;

	public static List<MenuVo> getMenuTree(List<Menu> menuList) {
		List<MenuVo> resultTree = new ArrayList<>();
		//先找一级树
		for (Menu menu : menuList){
			if (menu.getParent_id() == null || menu.getParent_id().equals("0")){
				MenuVo menuVo1 = new MenuVo();
				MenuVo menuVo = new MenuVo();
				menuVo.setTitle(menu.getTitle());
				menuVo.setCodePath(menu.getCode_path());
				menuVo.setId(menu.getId());
				//判断是不是终节点
				menuVo.setOver_End(menu.getEnd_mark());
				if (!menuVo.getOver_End().equals("1")){
					menuVo1 = getchild(menuVo,menuList);
				}
				resultTree.add(menuVo1);
			}
		}

		return resultTree;
	}


	private static MenuVo getchild(MenuVo menuVo,List<Menu> menuList){
		List<MenuVo> menuVos = new ArrayList<>();
		for (Menu menu:menuList){
			if (menu.getParent_id()!= null && menu.getParent_id().equals(menuVo.getId())){
				MenuVo menuVo1 = new MenuVo();
				menuVo1.setTitle(menu.getTitle());
				menuVo1.setCodePath(menu.getCode_path());
				menuVo1.setId(menu.getId());
				//判断是不是终节点
				menuVo1.setOver_End(menu.getEnd_mark());
				if (!menuVo1.getOver_End().equals("1")){
					MenuVo menuVo2 = getchild(menuVo1,menuList);
				}
				menuVos.add(menuVo1);
			}
		}
		menuVo.setChildren(menuVos);
		return menuVo;
	}


}
