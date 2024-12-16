package com.mangoe.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangoe.support.base.BaseController;
import com.mangoe.support.format.CamelMap;
import com.mangoe.user.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @throws Exception 
	 * @Description 사용자 관리
	 */
	@GetMapping("/retrieveList.do")
	public String retrieveList(Model model) throws Exception {
		List<Map<String, Object>> retrieveList = camelMapToHashMap(userService.retrieveList(new HashMap<>()));
		model.addAttribute("retrieveList", retrieveList);
		
		return "admin/user/retrieveList";
	}

	/**
	 * @Description 회원정보 수정
	 */
	@GetMapping("/retrieve.do")
	public String retrieve(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> user = userService.retrieve(authentication.getName());
		model.addAttribute("user",user);
		return "admin/user/retrieve";
	}
	
	/**
	 * @Description 유저 수정
	 */
	@PostMapping("/ajaxUpdate.do")
	@ResponseBody
	public Map<String, Object> ajaxUpdate(@RequestParam Map<String, Object> param) {
		ModelMap model = new ModelMap();
		try {
			param.put("bcryptPassword", passwordEncoder.encode(param.get("password").toString()));
			userService.update(param);
		} catch (PersistenceException e) {
			return setFailResult(model, e);
		}
		return setSuccessResult(model);
	}
	
	/**
	 * @Description 유저 삭제
	 */
	@PostMapping("/ajaxDelete.do")
	@ResponseBody
	public Map<String, Object> ajaxDelete(@RequestParam Map<String, Object> param) {
		ModelMap model = new ModelMap();
		try {
			userService.delete(param);
		} catch (PersistenceException e) {
			return setFailResult(model, e);
		}
		return setSuccessResult(model);
	}
	
	/**
	 * @Description 유저 조회
	 */
	@GetMapping("/ajaxRetrieveList.do")
	@ResponseBody
	public Map<String, Object> ajaxRetrieveList(@RequestParam Map<String, Object> param) {
		ModelMap model = new ModelMap();
		try {
			Map<String, Object> pagination = setGridPaging(param);
			List<Map<String, Object>> retrieveList =camelMapToHashMap(userService.retrieveList(param)); 
			Map<String, Object> data = setGridData(pagination, retrieveList);
			model.addAttribute("data", data);
		} catch (PersistenceException e) {
			return setFailResult(model, e);
		}
		return setSuccessResult(model);
	}
	
	
}
