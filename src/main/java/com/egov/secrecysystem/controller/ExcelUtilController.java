package com.egov.secrecysystem.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.egov.secrecysystem.service.ExcelUtilService;
import com.egov.secrecysystem.service.ProblemDanXuanService;
import com.egov.secrecysystem.service.ProblemDuoXuanService;
import com.egov.secrecysystem.service.ProblemPanDuanService;
import com.egov.secrecysystem.service.UserService;

@Controller
public class ExcelUtilController {

	private Logger logger = LoggerFactory.getLogger(ExcelUtilController.class);
	@Autowired
	private ExcelUtilService excelUtilService;
	@Autowired
	private UserService userservice;
	@Autowired
	private ProblemDanXuanService problemDanXuanService;
	@Autowired
	private ProblemDuoXuanService problemDuoXuanService;
	@Autowired
	private ProblemPanDuanService problemPanDuanService;

	// 批量上传保密人员信息
	@RequestMapping(value = "xlsToJsonAboutInstructor", method = RequestMethod.POST)
	public ModelAndView upLoadSingleFileAboutInstructor(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Writer out = response.getWriter();
		HttpSession curSession = request.getSession();
		// 判断是否上传了文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}

		InputStream sampleInputStream = null;
		InputStream fromInputStream = null;
		InputStream checksampleInputStream = null;
		InputStream checkfromInputStream = null;
		JSONObject result = null;
		List<JSONObject> instructors = null;

		// String schoolnum = curSession.getAttribute("institution").toString();
		try {
			logger.debug("上传的文件为：" + file.getOriginalFilename());
			logger.debug(request.getSession().getServletContext()
					.getRealPath("/")
					+ "sample/批量导入保密员信息sample.xls");
			sampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入保密员信息sample.xls");
			fromInputStream = file.getInputStream();
			checksampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入保密员信息sample.xls");
			checkfromInputStream = file.getInputStream();
			// 检查excel列
			String checkInfo = excelUtilService.checkExcelHeader(
					checkfromInputStream, checksampleInputStream);
			if (checkInfo.equals("passcheck")) {
				result = excelUtilService.convertstudentExcelToJson(
						fromInputStream, sampleInputStream);
				instructors = (List<JSONObject>) result.get("excelArray");
				if (userservice.checkInstructorsData(instructors) == null) {
					userservice.importInstructors(instructors);
					out.write("{ success: true, errors:{info: '上传成功！'}}");
				} else {
					out.write("{ success: false, errors:{info: '"
							+ userservice.checkInstructorsData(instructors)
							+ "！'}}");
				}
				;
			} else {
				out.write(checkInfo);
			}
			System.out.println(checkInfo);
			// logger.debug(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sampleInputStream.close();
				fromInputStream.close();
				checksampleInputStream.close();
				checkfromInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	// 批量上传单选题信息
	@RequestMapping(value = "xlsToJsonAboutDanXuan", method = RequestMethod.POST)
	public ModelAndView upLoadSingleFileAboutDanXuan(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Writer out = response.getWriter();
		HttpSession curSession = request.getSession();
		// 判断是否上传了文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}

		InputStream sampleInputStream = null;
		InputStream fromInputStream = null;
		InputStream checksampleInputStream = null;
		InputStream checkfromInputStream = null;
		JSONObject result = null;
		List<JSONObject> instructors = null;

		// String schoolnum = curSession.getAttribute("institution").toString();
		try {
			logger.debug("上传的文件为：" + file.getOriginalFilename());
			logger.debug(request.getSession().getServletContext()
					.getRealPath("/")
					+ "sample/批量导入单选题信息sample.xls");
			sampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入单选题信息sample.xls");
			fromInputStream = file.getInputStream();
			checksampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入单选题信息sample.xls");
			checkfromInputStream = file.getInputStream();
			// 检查excel列
			String checkInfo = excelUtilService.checkExcelHeader(
					checkfromInputStream, checksampleInputStream);
			if (checkInfo.equals("passcheck")) {
				result = excelUtilService.convertstudentExcelToJson(
						fromInputStream, sampleInputStream);
				instructors = (List<JSONObject>) result.get("excelArray");
				if (problemDanXuanService.checkInstructorsData(instructors) == null) {
					problemDanXuanService.importInstructors(instructors);
					out.write("{ success: true, errors:{info: '上传成功！'}}");
				} else {
					out.write("{ success: false, errors:{info: '"
							+ userservice.checkInstructorsData(instructors)
							+ "！'}}");
				}
				;
			} else {
				out.write(checkInfo);
			}
			System.out.println(checkInfo);
			// logger.debug(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sampleInputStream.close();
				fromInputStream.close();
				checksampleInputStream.close();
				checkfromInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	// 批量上传多选题信息
	@RequestMapping(value = "xlsToJsonAboutDuoXuan", method = RequestMethod.POST)
	public ModelAndView upLoadSingleFileAboutDuoXuan(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Writer out = response.getWriter();
		HttpSession curSession = request.getSession();
		// 判断是否上传了文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		if (file.getSize() == 0) {
			out.write("{success: false, errors:{info: '未选择文件！'}}");
			return null;
		}

		InputStream sampleInputStream = null;
		InputStream fromInputStream = null;
		InputStream checksampleInputStream = null;
		InputStream checkfromInputStream = null;
		JSONObject result = null;
		List<JSONObject> instructors = null;

		// String schoolnum = curSession.getAttribute("institution").toString();
		try {
			logger.debug("上传的文件为：" + file.getOriginalFilename());
			logger.debug(request.getSession().getServletContext()
					.getRealPath("/")
					+ "sample/批量导入多选题信息sample.xls");
			sampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入多选题信息sample.xls");
			fromInputStream = file.getInputStream();
			checksampleInputStream = new FileInputStream(request.getSession()
					.getServletContext().getRealPath("/")
					+ "sample/批量导入多选题信息sample.xls");
			checkfromInputStream = file.getInputStream();
			// 检查excel列
			String checkInfo = excelUtilService.checkExcelHeader(
					checkfromInputStream, checksampleInputStream);
			if (checkInfo.equals("passcheck")) {
				result = excelUtilService.convertstudentExcelToJson(
						fromInputStream, sampleInputStream);
				instructors = (List<JSONObject>) result.get("excelArray");
				if (problemDuoXuanService.checkInstructorsData(instructors) == null) {
					problemDuoXuanService.importInstructors(instructors);
					out.write("{ success: true, errors:{info: '上传成功！'}}");
				} else {
					out.write("{ success: false, errors:{info: '"
							+ userservice.checkInstructorsData(instructors)
							+ "！'}}");
				}
				;
			} else {
				out.write(checkInfo);
			}
			System.out.println(checkInfo);
			// logger.debug(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sampleInputStream.close();
				fromInputStream.close();
				checksampleInputStream.close();
				checkfromInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	// 批量上传判断题信息
		@RequestMapping(value = "xlsToJsonAboutPanDuan", method = RequestMethod.POST)
		public ModelAndView upLoadSingleFileAboutPanDuan(
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Writer out = response.getWriter();
			HttpSession curSession = request.getSession();
			// 判断是否上传了文件
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
					.getFile("file");
			if (file.getSize() == 0) {
				out.write("{success: false, errors:{info: '未选择文件！'}}");
				return null;
			}

			InputStream sampleInputStream = null;
			InputStream fromInputStream = null;
			InputStream checksampleInputStream = null;
			InputStream checkfromInputStream = null;
			JSONObject result = null;
			List<JSONObject> instructors = null;

			// String schoolnum = curSession.getAttribute("institution").toString();
			try {
				logger.debug("上传的文件为：" + file.getOriginalFilename());
				logger.debug(request.getSession().getServletContext()
						.getRealPath("/")
						+ "sample/批量导入判断题信息sample.xls");
				sampleInputStream = new FileInputStream(request.getSession()
						.getServletContext().getRealPath("/")
						+ "sample/批量导入判断题信息sample.xls");
				fromInputStream = file.getInputStream();
				checksampleInputStream = new FileInputStream(request.getSession()
						.getServletContext().getRealPath("/")
						+ "sample/批量导入判断题信息sample.xls");
				checkfromInputStream = file.getInputStream();
				// 检查excel列
				String checkInfo = excelUtilService.checkExcelHeader(
						checkfromInputStream, checksampleInputStream);
				if (checkInfo.equals("passcheck")) {
					result = excelUtilService.convertstudentExcelToJson(
							fromInputStream, sampleInputStream);
					instructors = (List<JSONObject>) result.get("excelArray");
					if (problemPanDuanService.checkInstructorsData(instructors) == null) {
						problemPanDuanService.importInstructors(instructors);
						out.write("{ success: true, errors:{info: '上传成功！'}}");
					} else {
						out.write("{ success: false, errors:{info: '"
								+ userservice.checkInstructorsData(instructors)
								+ "！'}}");
					}
					;
				} else {
					out.write(checkInfo);
				}
				System.out.println(checkInfo);
				// logger.debug(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					sampleInputStream.close();
					fromInputStream.close();
					checksampleInputStream.close();
					checkfromInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
}
