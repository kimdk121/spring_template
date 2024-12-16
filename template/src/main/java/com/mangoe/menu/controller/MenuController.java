package com.mangoe.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangoe.menu.service.MenuService;
import com.mangoe.support.base.BaseController;
import com.mangoe.support.format.CamelMap;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * @Description 메뉴 호출
	 */
	@PostMapping("/ajaxUserMenu.do")
	@ResponseBody
	public Map<String, Object> ajaxUserMenu(@RequestParam Map<String, Object> param) {
		ModelMap model = new ModelMap();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> userRole = authentication.getAuthorities().stream().map(t -> t.getAuthority().toString()).toList();
		List<Map<String, Object>> resultList;
		try {
			resultList = camelMapToHashMap(menuService.userMenuList(userRole.getFirst()));
		} catch (PersistenceException e) {
			return setFailResult(model, e);
		}
		List<Map<String, Object>> upperMenuList = resultList.stream().filter(t -> t.get("upperMenuId").equals("root")).toList();
		
		for(Map<String, Object> upperMenu : upperMenuList) {
			List<Map<String, Object>> subMenuList = new ArrayList<>();
			for(Map<String, Object> result : resultList) {
				if(result.get("upperMenuId").toString().equals(upperMenu.get("menuId"))) {
					subMenuList.add(result);
				}
			}
			upperMenu.put("subMenuList", subMenuList);
		}
		model.addAttribute("menuList",upperMenuList);
		
		return setSuccessResult(model);
	}
	
}
