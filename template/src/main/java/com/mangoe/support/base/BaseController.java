package com.mangoe.support.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangoe.support.format.CamelMap;

public class BaseController implements BaseConstant {
	
	public Map<String, Object> setSuccessResult(Map<String, Object> model) {
		model.put(KEY_RESULT_CODE, CODE_SUCCESS);
		model.put(KEY_RESULT_MESSAGE, RESULT_SUCCESS);
		model.put(GRID_RESULT_KEY, true);
		return model;
	}
	
	public Map<String, Object> setFailResult(Map<String, Object> model, Exception e) {
		model.put(KEY_RESULT_CODE, CODE_FAIL);
		model.put(KEY_RESULT_MESSAGE, RESULT_FAIL);
		model.put(GRID_RESULT_KEY, false);
		model.put(EXCEPTION_NAME, e.toString());
		model.put(EXCEPTION_MESSAGE, e.getMessage());
		return model;
	}
	
	public Map<String, Object> setGridPaging(Map<String, Object> param) {
		int page = Integer.parseInt(param.get("page").toString());
		int perPage =Integer.parseInt(param.get("perPage").toString());	
		int firstRecordIndex = ((page-1) * perPage) ; 
		int lastRecordIndex = ((page-1)  * perPage) + perPage ;
		param.put("firstRecordIndex", firstRecordIndex); 
		param.put("lastRecordIndex", lastRecordIndex);
		
		Map<String, Object> paginationMap = new HashMap<>();
		paginationMap.put("page", page);
		
		return paginationMap;
	}
	
	public Map<String, Object> setGridData(Map<String, Object> pagination, List<Map<String, Object>> retrieveList) {
		Map<String, Object> data = new HashMap<>();
		if(retrieveList.size() > 0) 
			pagination.put("totalCount",retrieveList.getFirst().get("totalCount").toString());
		data.put("pagination", pagination);
		data.put("contents", retrieveList);
		return data;
	}		
	
	public Map<String, Object> camelMapToHashMap (CamelMap camelMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>(camelMap);
		return returnMap;
	}

	public List<Map<String, Object>> camelMapToHashMap (List<CamelMap> camelMapList) {
		List<Map<String, Object>> returnMap = camelMapList.stream().map(t -> camelMapToHashMap(t)).toList();
		return returnMap;
	}
	
	public String mapToJson(Map<String, Object> map) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
}
