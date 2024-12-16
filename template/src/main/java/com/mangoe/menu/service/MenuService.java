package com.mangoe.menu.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangoe.support.format.CamelMap;

@Service
public class MenuService {
	
	@Autowired
	private MenuMapper menuMapper;
	
	public List<CamelMap> userMenuList(String userId) throws PersistenceException {
		return menuMapper.userMenuList(userId);
	}
	
}
