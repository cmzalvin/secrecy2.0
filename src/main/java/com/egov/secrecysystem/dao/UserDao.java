package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.egov.secrecysystem.model.SeUser;

public class UserDao extends AbstractHibernateDao<SeUser> {

	UserDao() {
		super(SeUser.class);
	}

	// 验证登陆
	public String validateLogin(String name, String password) {
		String hql = "select e from com.egov.secrecysystem.model.SeUser e where e.loginname=:Name and e.password=:Password";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("Name", name);
		query.setParameter("Password", password);
		List<SeUser> result = query.list();
		if (result.size() != 1)
			return null;
		else
			return "ok";
	}

	public SeUser getRoleByLoginName(String loginName) {
		String hql = "select e from com.egov.secrecysystem.model.SeUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<SeUser> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}

	public Integer getRoleLevelByLoginName(String loginName) {
		String hql = "select e.userlevel from com.egov.secrecysystem.model.SeUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<String> result = query.list();
		if (result.size() == 1)
			return Integer.parseInt(result.get(0));
		return -1;
	}

	public List<Map> getPageInstructors(String whereString,
			Map<String, Object> valuesMap, int pageNum, int pageSize) {
		String hql = "select new map(t.id as id, t.name as name, t.loginname as loginname, t.password as password,"
				+ "t.userlevel as userlevel, t.score as score) from com.egov.secrecysystem.model.SeUser t";
		hql += whereString;
		hql += " order by t.id ";
		// select new map(t.id as id, t.name as name, t.loginname as loginname,
		// t.password as password,t.userlevel as userlevel, t.score as score)
		// from com.egov.secrecysystem.model.SeUser t where 1=1 and
		// seuser.loginname = order by t.id";
		Query query = this.getCurrentSession().createQuery(hql)
				.setFirstResult(pageNum).setMaxResults(pageSize);
		/*
		 * Session currentSession = this.getCurrentSession(); Query query =
		 * currentSession
		 * .createQuery(hql).setFirstResult(pageNum).setMaxResults(pageSize);
		 */

		String[] perchs = valuesMap.keySet().toArray(new String[0]);

		for (int i = 0; i < perchs.length; i++) {
			if (perchs[i].equals("score")) {
				query.setParameter(perchs[i],
						Integer.parseInt((String) valuesMap.get(perchs[i])));
			} else {
				query.setParameter(perchs[i], valuesMap.get(perchs[i]));
			}
		}
		return query.list();
	}

	public long getInstructorsTotalCount(String whereString,
			Map<String, Object> valuesMap) {
		String hql = "select t from com.egov.secrecysystem.model.SeUser t ";
		hql += whereString;
		hql += " and t is not null";
		Query query = this.getCurrentSession().createQuery(hql);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);
		for (int i = 0; i < perchs.length; i++) {
			query.setParameter(perchs[i], valuesMap.get(perchs[i]));
		}
		return query.list().size();
	}

	public Session getSession() {
		return this.getCurrentSession();
	}

	public List<Map> getInstructorsInfoExcel() {

		String hql = "select new map(e.name as name,e.loginname as loginname,"
				+ "e.password as password,e.userlevel as userlevel,"
				+ "e.score as score) "
				+ "from   com.egov.secrecysystem.model.SeUser e ";
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();

	}

	// 返回得到的密码
	public String getRolePassword(String loginName) {
		String hql = "select e.password from com.egov.secrecysystem.model.SeUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<String> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}

	// 通过loginname设置密码
	public boolean setRolePassword(String loginName, String password) {
		try {
			SeUser entity = this.getUserByLoginName(loginName);
			if (entity == null)
				return false;
			entity.setPassword(password);
			this.update(entity);
			return true;
		} catch (Exception e) {
			System.out.println("savePassword error!" + e.getMessage());
			return false;
		}
	}

	// 通过loginname得到user对象
	public SeUser getUserByLoginName(String loginName) {
		String hql = "select e from com.egov.secrecysystem.model.SeUser e where e.loginname=:loginName";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("loginName", loginName);
		List<SeUser> result = query.list();
		if (result.size() == 1)
			return result.get(0);
		return null;
	}

}
