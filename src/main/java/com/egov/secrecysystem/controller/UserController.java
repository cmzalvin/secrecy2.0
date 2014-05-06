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
import com.egov.secrecysystem.service.UserService;

@RemoteProxy(creator = SpringCreator.class)
public class UserController {

	@Autowired
	private UserService userservice;
	
	@RemoteMethod
	public Map paginationShow(String filterString, String pageNum, String pageSize) {
		//String schoolnum = (String) WebContextFactory.get().getSession().getAttribute("institution");
		Map map = new HashMap();
		String whereString = Function.initSearchHql_whereString_(filterString);
		Map<String, Object> paramsMap = Function.initSearchValues(filterString);
		List<Map> result = userservice.getPageInstructors(whereString, paramsMap, pageNum, pageSize);
		map.put("totalProperty", userservice.getInstructorsTotalCount(whereString, paramsMap));
		map.put("root", result);
		return map;
	}
	@RemoteMethod
	public boolean updateInstructorAllInfo(JSONObject instructor){
	//	String schoolnum = (String) WebContextFactory.get().getSession().getAttribute("institution");		
			return userservice.updateInstructorAllInfo(instructor);
	}
	@RemoteMethod
	public boolean deleteInstructors(List<String> ids){
		return userservice.deleteInstructors(ids);
	}
}
