package com.egov.secrecysystem.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.egov.secrecysystem.model.SeProblemPanDuan;

public class ProblemPanDuanDao extends AbstractHibernateDao<SeProblemPanDuan> {
	ProblemPanDuanDao() {
		super(SeProblemPanDuan.class);
	}

	public Session getSession() {
		return this.getCurrentSession();
	}

	public List<Map> getPageInstructors(String whereString,
			Map<String, Object> valuesMap, int pageNum, int pageSize) {
		String hql = "select new map(t.id as id, t.problemlevel as problemlevel, t.mustright as mustright, t.keyproblem as keyproblem,"
				+ "t.question as question, t.option1 as option1, t.option2 as option2,"
				+ "t.answer as answer,t.problemnum as problemnum) from com.egov.secrecysystem.model.SeProblemPanDuan t";
		hql += whereString;
		hql += " order by t.id ";
		Query query = this.getCurrentSession().createQuery(hql)
				.setFirstResult(pageNum).setMaxResults(pageSize);
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
		String hql = "select t from com.egov.secrecysystem.model.SeProblemPanDuan t ";
		hql += whereString;
		hql += " and t is not null";
		Query query = this.getCurrentSession().createQuery(hql);
		String[] perchs = valuesMap.keySet().toArray(new String[0]);
		for (int i = 0; i < perchs.length; i++) {
			query.setParameter(perchs[i], valuesMap.get(perchs[i]));
		}
		return query.list().size();
	}

	public List<Map> getInstructorsInfoExcel() {

		String hql = "select new map(t.id as id, t.problemlevel as problemlevel, t.mustright as mustright, t.keyproblem as keyproblem,"
				+ "t.question as question, t.option1 as option1, t.option2 as option2,  "
				+ "t.answer as answer,t.problemnum as problemnum) from com.egov.secrecysystem.model.SeProblemPanDuan t";
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();

	}

}