package com.mangoe.support.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mangoe.support.format.CamelMap;
import com.mangoe.user.service.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		CamelMap user = userMapper.retrieve(userId);
		if(user == null) {
			return null;
		}
			user.computeIfAbsent("userId", t -> "");
			user.computeIfAbsent("bcryptPassword", t -> "");
			user.computeIfAbsent("userName", t -> "");
			user.computeIfAbsent("userRole", t -> "");
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.get("userRole").toString()));
		
		return new CustomUserDetails(user,authorities);
	}

}
