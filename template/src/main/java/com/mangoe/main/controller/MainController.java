package com.mangoe.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mangoe.support.base.BaseController;

@Controller
public class MainController extends BaseController {
	
	/**
	 * @Description 리다이렉트
	 */
	@GetMapping("/")
	public String redirectForm(Model model) {
		
		return "redirect:/mainForm.do";
	}
	
	/**
	 * @Description 메인 화면 호출
	 */
	@GetMapping("/mainForm.do")
	public String mainForm(Model model) {
		
		return "main";
	}
	
}
