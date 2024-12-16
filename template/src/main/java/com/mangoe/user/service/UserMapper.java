package com.mangoe.user.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.exceptions.PersistenceException;

import com.mangoe.support.format.CamelMap;

@Mapper
public interface UserMapper {
	
	public List<CamelMap> retrieveList(Map<String, Object> param) throws PersistenceException;
	
	public CamelMap retrieve(String userId) throws PersistenceException;
	
	public int insert (Map<String, Object> param) throws PersistenceException;
	
	public int update (Map<String, Object> param) throws PersistenceException;

	public int delete (Map<String, Object> param) throws PersistenceException;
}
