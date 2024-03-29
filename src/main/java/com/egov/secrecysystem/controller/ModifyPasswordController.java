package com.egov.secrecysystem.controller;

import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;

import com.egov.secrecysystem.service.UserService;

@RemoteProxy(creator = SpringCreator.class)
public class ModifyPasswordController {

	@Autowired
	private UserService userService;

	//private ExUser exUser;

	@RemoteMethod
	public String setRolePassword(Map passwordMap) {
		String loginName = (String) WebContextFactory.get().getSession()
				.getAttribute("name");
//		String institution = (String) WebContextFactory.get().getSession()
//				.getAttribute("institution");

		String oldPassword = (String) passwordMap.get("oldPassword");
		String newPassword = (String) passwordMap.get("newPassword");
		if (loginName == null || oldPassword == null || newPassword == null)
			return "{ success: false, errors:{info: '更新密码错误，请重试'}}";

		if (loginName.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty())
			return "{ success: false, errors:{info: '更新密码错误，请重试'}}";
		
		String persistedPassword=userService.getRolePassword(loginName);
		if(persistedPassword==null)
			return "{ success: false, errors:{info: '无法找到当前用户，请重新登陆'}}";
		if (oldPassword.equals(persistedPassword)) {
			if(userService.setRolePassword(loginName, newPassword))
				return "{ success: true, errors:{info: '成功更新密码'}}";
			else
				return "{ success: false, errors:{info: '原密码错误，请重试'}}";
		}else{
			return "{ success: false, errors:{info: '原密码错误，请重试'}}";
		}
	}

}
