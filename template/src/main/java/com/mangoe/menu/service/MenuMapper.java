package com.mangoe.menu.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.mangoe.support.format.CamelMap;

@Mapper
public interface MenuMapper {
	
	public List<CamelMap> userMenuList(String userRole) throws PersistenceException ;
	
}
