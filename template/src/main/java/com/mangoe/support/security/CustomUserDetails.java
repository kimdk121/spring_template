package com.mangoe.support.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mangoe.support.format.CamelMap;

public class CustomUserDetails extends User {

	private String userId;
	private String password; 
	private String userName;
	
	public CustomUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
		super(userId, password, authorities);
	}
	
	public CustomUserDetails(CamelMap user, Collection<? extends GrantedAuthority> authorities) {
		super(user.get("userId").toString(), user.get("bcryptPassword").toString() , authorities);
		this.userId = user.get("userId").toString();
		this.userName = user.get("userName").toString();
		this.password = user.get("bcryptPassword").toString();
	}
	


}
