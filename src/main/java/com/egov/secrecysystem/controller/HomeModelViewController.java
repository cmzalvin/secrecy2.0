package com.egov.secrecysystem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.egov.secrecysystem.service.MainMenuService;
import com.egov.secrecysystem.service.RoleService;
import com.egov.secrecysystem.service.UserService;

@Controller
public class HomeModelViewController {

	@Autowired
	private MainMenuService mainMenuService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;



	@RequestMapping(value = "Home", method = RequestMethod.GET)
	public ModelAndView getindexPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer roleNum=0;
		ModelAndView model = new ModelAndView("Home");
		HttpSession curSession = request.getSession();
		
		roleNum=(Integer)curSession.getAttribute("role");
		String institution=(String) curSession.getAttribute("remark");
		String username = (String)curSession.getAttribute("name");
		model.addObject("menus", mainMenuService.getAuthorisedMenu(roleNum));
		if(roleNum==0)
		{			
			model.addObject("username","管理员");
			model.addObject("institution",institution);
		}
		else{
			model.addObject("username", username);
			model.addObject("institution",institution);
		}
				
		return model;
	}
}
