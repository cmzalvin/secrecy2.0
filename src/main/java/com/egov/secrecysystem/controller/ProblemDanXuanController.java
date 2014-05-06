package com.egov.secrecysystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.model.Function;
import com.egov.secrecysystem.service.ProblemDanXuanService;


@RemoteProxy(creator = SpringCreator.class)
public class ProblemDanXuanController {
	@Autowired
	private ProblemDanXuanService problemDanXuanService;

	@RemoteMethod
	public Map paginationShow(String filterString, String pageNum,
			String pageSize) {
		
		Map map = new HashMap();
		String whereString = Function.initSearchHql_whereString_(filterString);
		Map<String, Object> paramsMap = Function.initSearchValues(filterString);
		List<Map> result = problemDanXuanService.getPageInstructors(
				whereString, paramsMap, pageNum, pageSize);
		map.put("totalProperty", problemDanXuanService
				.getInstructorsTotalCount(whereString, paramsMap));
		map.put("root", result);
		return map;
	}

	@RemoteMethod
	public boolean updateInstructorAllInfo(JSONObject instructor) {
		return problemDanXuanService.updateInstructorAllInfo(instructor);
	}
	@RemoteMethod
	public boolean deleteInstructors(List<String> ids){
		return problemDanXuanService.deleteInstructors(ids);
	}
	
}
