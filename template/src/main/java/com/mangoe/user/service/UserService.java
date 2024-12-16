package com.mangoe.user.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mangoe.support.format.CamelMap;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public List<CamelMap> retrieveList(Map<String, Object> param) throws PersistenceException {
		return userMapper.retrieveList(param);
	}
	
	public CamelMap retrieve(String userId) throws PersistenceException {
		return userMapper.retrieve(userId);
	}
	
	public int insert(Map<String, Object> param) throws PersistenceException {
		return userMapper.insert(param);
	}
	
	public int update(Map<String, Object> param) throws PersistenceException {
		return userMapper.update(param);
	}
	
	public int delete(Map<String, Object> param) throws PersistenceException {
		return userMapper.delete(param);
	}
}
