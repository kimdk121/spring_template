package com.mangoe.support.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mangoe.support.security.CustomAuthenticationFailureHandler;
import com.mangoe.support.security.CustomAuthenticationProvider;
import com.mangoe.support.security.CustomAuthenticationSuccessHandler;
import com.mangoe.support.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
	    // CSRF 설정
	    http.csrf((csrf) ->	csrf.disable())
	    .authorizeHttpRequests((authorizedHttpRequests) -> 
	    	authorizedHttpRequests   
	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()// /static/css/** , /static/js/** , /static/images/** , /static/webjars/** , /static/favicon.*, /static/*/icon-*
	            .requestMatchers("/login/**").permitAll() //,"/templates/fragments/**"
	            .anyRequest().authenticated()
	    ).formLogin((formLogin) -> 
	    	formLogin
	    		.loginPage("/login/loginForm.do")
	    		.usernameParameter("userId")
	    		.passwordParameter("password")
	    		.loginProcessingUrl("/loginProcess")
	    		//.defaultSuccessUrl("/login/loginSuccess.do")
	    		.successHandler(new CustomAuthenticationSuccessHandler("/login/loginSuccess.do"))
	    		//.failureUrl("/login/loginFail.do")
	            .failureHandler(new CustomAuthenticationFailureHandler("/login/loginFail.do"))
	    ).logout((logout) -> 
	    	logout
	    		.logoutUrl("/logout")
	    		.invalidateHttpSession(true)
	    		.logoutSuccessHandler((request, response, authentication) -> {
	    			response.sendRedirect("/login/logoutSuccess.do");
	    		})
	    );

	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService , passwordEncoder());
    }
    
}
