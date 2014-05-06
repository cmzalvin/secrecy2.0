package com.egov.secrecysystem.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egov.secrecysystem.dao.ProblemPanDuanDao;
import com.egov.secrecysystem.model.SeProblemPanDuan;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProblemPanDuanService {

	@Autowired
	private ProblemPanDuanDao problemPanDuandao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Map> getPageInstructors(String whereString,
			Map<String, Object> valuesMap, String pageNum, String pageSize) {
		try {
			return problemPanDuandao.getPageInstructors(whereString, valuesMap,
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
			return problemPanDuandao.getInstructorsTotalCount(whereString,
					valuesMap);
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
				SeProblemPanDuan entity = problemPanDuandao.findById(instructor
						.get("id").toString());

				entity.setProblemlevel(instructor.get("problemlevel")
						.toString());
				entity.setMustright(instructor.get("mustright").toString());
				entity.setKeyproblem(instructor.get("keyproblem").toString());
				entity.setQuestion(instructor.get("question").toString());
				entity.setOption1(instructor.get("option1").toString());
				entity.setOption2(instructor.get("option2").toString());
				entity.setAnswer(instructor.get("answer").toString());
				entity.setProblemnum(instructor.get("problemnum").toString());

				problemPanDuandao.update(entity);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean saveInstructor(JSONObject instructor) {

		try {

			SeProblemPanDuan entity = new SeProblemPanDuan();

			entity.setProblemlevel(instructor.get("problemlevel").toString());
			entity.setMustright(instructor.get("mustright").toString());
			entity.setKeyproblem(instructor.get("keyproblem").toString());
			entity.setQuestion(instructor.get("question").toString());
			entity.setOption1(instructor.get("option1").toString());
			entity.setOption2(instructor.get("option2").toString());
			entity.setAnswer(instructor.get("answer").toString());
			entity.setProblemnum(instructor.get("problemnum").toString());

			problemPanDuandao.save(entity);
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
				SeProblemPanDuan entity = problemPanDuandao.findById(id);
				problemPanDuandao.delete(entity);
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
			SeProblemPanDuan entity;
			for (JSONObject instructor : instructors) {
				entity = new SeProblemPanDuan();

				entity.setProblemlevel(instructor.get("problemlevel")
						.toString());
				entity.setMustright(instructor.get("mustright").toString());
				entity.setKeyproblem(instructor.get("keyproblem").toString());
				entity.setQuestion(instructor.get("question").toString());
				entity.setOption1(instructor.get("option1").toString());
				entity.setOption2(instructor.get("option2").toString());
				entity.setAnswer(instructor.get("answer").toString());
				entity.setProblemnum(instructor.get("problemnum").toString());

				problemPanDuandao.save(entity);
				if (instructorcount % 50 == 0) {
					problemPanDuandao.getSession().flush();
					problemPanDuandao.getSession().clear();
				}
				instructorcount++;
			}
			return true;
		} catch (Exception e) {
			System.out.println("importInstructors error");
			e.printStackTrace();
			problemPanDuandao.getSession().getTransaction().rollback();
			return false;
		}
	}
	// 批量导出信息
		@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
		public List<Map> getInstructorsInfoExcel() {
			try {
				return problemPanDuandao.getInstructorsInfoExcel();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

}
