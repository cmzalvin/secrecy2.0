package com.egov.secrecysystem.service;

import java.util.List;
import java.util.Map;

import com.egov.secrecysystem.model.SeUser;
import com.egov.secrecysystem.utility.Md5;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.RoleDao;
import com.egov.secrecysystem.dao.UserDao;
//import cn.hdu.examsignup.dao.InstitutionDao;
//import org.resource.model.ExInstitution;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	private RoleDao roledao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String validateLogin(String name, String password) {
		String temp = this.userDao.validateLogin(name, password);
		return temp;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public SeUser getRoleByLoginName(String loginName) {
		return this.userDao.getRoleByLoginName(loginName);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer getRoleLevelByLoginName(String loginName) {
		return userDao.getRoleLevelByLoginName(loginName);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getPageInstructors(String whereString,
			Map<String, Object> valuesMap, String pageNum, String pageSize) {
		try {
			return userDao.getPageInstructors(whereString, valuesMap,
					Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		} catch (Exception e) {
			System.out.println("getPageInstructors error");
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long getInstructorsTotalCount(String whereString,
			Map<String, Object> valuesMap) {
		try {
			return userDao.getInstructorsTotalCount(whereString, valuesMap);
		} catch (Exception e) {
			System.out.println("getInstructorsTotalCount error");
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean updateInstructorAllInfo(JSONObject instructor) {

		if (instructor.get("id").toString().equals("")) {
			return this.saveInstructor(instructor);
		} else {
			try {

				SeUser entity = userDao.findById(instructor.get("id")
						.toString());
				//String str = instructor.get("password").toString();
				//Md5 md5Encrypt = new Md5();
				//String password = md5Encrypt.md5(str);
				entity.setName(instructor.get("name").toString());
				entity.setLoginname(instructor.get("loginname").toString());
				entity.setPassword(instructor.get("password").toString());
				entity.setUserlevel(instructor.get("userlevel").toString());
				if (instructor.get("score").equals("")) {
					entity.setScore(null);
				} else {
					entity.setScore(Integer.parseInt(instructor.get("score")
							.toString()));
				}

				userDao.update(entity);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	private Md5 Md5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveInstructor(JSONObject instructor) {

		try {

			SeUser entity = new SeUser();
			String str = instructor.get("password").toString();
			Md5 md5Encrypt = new Md5();
			String password = md5Encrypt.md5(str);
			entity.setName(instructor.get("name").toString());
			entity.setLoginname(instructor.get("loginname").toString());
			entity.setPassword(password);
			entity.setUserlevel(instructor.get("userlevel").toString());
			if (instructor.get("score").equals("")) {
				entity.setScore(null);
			} else {
				entity.setScore(Integer.parseInt(instructor.get("score")
						.toString()));

			}
			userDao.save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean deleteInstructors(List<String> ids) {

		try {
			for (String id : ids) {
				SeUser entity = userDao.findById(id);
				userDao.delete(entity);
			}
			return true;
		} catch (Exception e) {
			System.out.println("deleteInstructors error");
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String checkInstructorsData(List<JSONObject> instructors) {
		try {
			int instructorcount = 1;
			for (JSONObject instructor : instructors) {
				instructorcount++;
			}
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean importInstructors(List<JSONObject> instructors) {
		try {
			int instructorcount = 0;
			SeUser entity;
			for (JSONObject instructor : instructors) {
				entity = new SeUser();
				String str = instructor.get("password").toString();
				Md5 md5Encrypt = new Md5();
				String password = md5Encrypt.md5(str);
				entity.setName(instructor.get("name").toString());
				entity.setLoginname(instructor.get("loginname").toString());
				entity.setPassword(password);
				entity.setUserlevel(instructor.get("userlevel").toString());
				if (instructor.get("score").equals("")) {
					entity.setScore(null);
				} else {
					entity.setScore(Integer.parseInt(instructor.get("score")
							.toString()));
				}
				userDao.save(entity);
				if (instructorcount % 50 == 0) {
					userDao.getSession().flush();
					userDao.getSession().clear();
				}
				instructorcount++;
			}
			return true;
		} catch (Exception e) {
			System.out.println("importInstructors error");
			e.printStackTrace();
			userDao.getSession().getTransaction().rollback();
			return false;
		}
	}

	// 批量导出信息
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getInstructorsInfoExcel() {
		try {
			return userDao.getInstructorsInfoExcel();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 通过loginname得到登陆密码
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getRolePassword(String loginName) {
		return this.userDao.getRolePassword(loginName);
	}

	// 通过用户名设置密码
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean setRolePassword(String loginName, String password) {
		return this.userDao.setRolePassword(loginName, password);
	}

}
