package com.mangoe.support.format;

import java.util.HashMap;

import org.apache.ibatis.type.Alias;

import com.google.common.base.CaseFormat;

@Alias("CamelMap")
public class CamelMap extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1l;
	
	@Override
	public Object put(String key, Object value) {
		return super.put(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) key), value);
	}
	/*
	public Object put(String key, Object value, boolean isCamel) {
		if(isCamel) return super.put(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) key), value);
		else return super.put(key, value);
	}
	*/
	
}
