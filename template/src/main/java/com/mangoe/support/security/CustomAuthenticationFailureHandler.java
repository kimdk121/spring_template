package com.mangoe.support.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String failUrl;
	
	public CustomAuthenticationFailureHandler(String failUrl) {
		this.failUrl = failUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
        String errorMsg = null;

        if(exception instanceof BadCredentialsException){
        	errorMsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        }
        /*
        else if(exception instanceof DisabledException) {
            errorMsg = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
        }
        else if(exception instanceof LockedException){
            errorMsg = "이메일이 인증되지 않았습니다. 이메일을 확인해 주세요.";
        }*/
        else{
        	errorMsg = "알수없는 이유로 로그인에 실패하였습니다.";
        }
        request.setAttribute("errorMsg", errorMsg);
        request.getRequestDispatcher(failUrl).forward(request, response);
	}
	
}
