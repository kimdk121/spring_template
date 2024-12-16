package com.mangoe.support.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private String successUrl;
	
	public CustomAuthenticationSuccessHandler(String successUrl) {
		this.successUrl = successUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		request.setAttribute("userId", authentication.getName());
        request.getRequestDispatcher(successUrl).forward(request, response);
		
	}

}
