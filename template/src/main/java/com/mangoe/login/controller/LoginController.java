package com.mangoe.login.controller;

import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangoe.support.base.BaseController;
import com.mangoe.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @Description 로그인 화면 호출
	 */
	@GetMapping("loginForm.do")
	public String loginForm(Model model) {
		return "login/login";
	}
	
	/**
	 * @Description 회원가입 화면 호출
	 */
	@GetMapping("signupForm.do")
	public String signupForm(Model model) {
		
		return "login/signup";
	}	
	
	/**
	 * @Description 로그인 실패 화면 호출
	 */
	@PostMapping("loginFail.do")
	public String loginFailForm(HttpServletRequest httpServletRequest, Model model) {
		model.addAttribute("errorMsg",httpServletRequest.getAttribute("errorMsg"));
		return "login/loginFail";
	}
	
	/**
	 * @Description 로그인 성공 화면 호출
	 */
	@PostMapping("loginSuccess.do")
	public String loginSuccessForm(HttpServletRequest httpServletRequest, Model model) {
		model.addAttribute("userId",httpServletRequest.getAttribute("userId"));
		return "login/loginSuccess";
	}
	
	/**
	 * @Description 로그아웃 성공 화면 호출
	 */
	@GetMapping("logoutSuccess.do")
	public String logoutSuccessForm(Model model) {
		
		return "login/logoutSuccess";
	}
	
	/**
	 * @Description 회원가입 화면 호출
	 */
	@PostMapping("ajaxSignup.do")
	@ResponseBody
	public Map<String, Object> signup(@RequestParam Map<String, Object> param) {
		ModelMap model = new ModelMap();
		try {
			param.put("bcryptPassword", passwordEncoder.encode(param.get("password").toString()));
			model.addAttribute("result",userService.insert(param));
		}
		catch (PersistenceException e) {
			return setFailResult(model, e);
		}
		return setSuccessResult(model);
	}	
}
