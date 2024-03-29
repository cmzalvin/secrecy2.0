package com.egov.secrecysystem.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import cn.hdu.examsignup.model.ExSupervisor;
//import cn.hdu.examsignup.model.Function;



import com.egov.secrecysystem.service.ProblemDanXuanService;
import com.egov.secrecysystem.service.ProblemDuoXuanService;
import com.egov.secrecysystem.service.ProblemPanDuanService;
import com.egov.secrecysystem.service.UserService;

@Controller
public class ExportExcelController {

	@Autowired
	private UserService userservice;
	@Autowired
	private ProblemDanXuanService problemDanXuanService;
	@Autowired
	private ProblemDuoXuanService problemDuoXuanService;
	@Autowired
	private ProblemPanDuanService problemPanDuanService;

	// 批量导出保密人员信息
	@RequestMapping(value = "exportInstructorsInfoExcel", method = RequestMethod.POST)
	public ModelAndView exportInstructorsInfoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.reset();
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "secrecysInfo"
				+ date.format(new Date()).toString();
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Content-disposition", "attachment;filename="
					+ filename + ".xls");
			WritableSheet sheet = wwb.createSheet("所有辅导员信息", 0);
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 25);

			WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
					WritableFont.BOLD, false);
			WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
			Label label = null;
			label = new Label(0, 0, "姓名");
			sheet.addCell(label);
			label = new Label(1, 0, "登录用户");
			sheet.addCell(label);
			label = new Label(2, 0, "密码");
			sheet.addCell(label);
			label = new Label(3, 0, "用户等级");
			sheet.addCell(label);
			label = new Label(4, 0, "分数");
			sheet.addCell(label);

			List<Map> instructors = userservice.getInstructorsInfoExcel();
			for (int i = 0; i < instructors.size(); i++) {
				label = new Label(0, i + 1, instructors.get(i).get("name")
						.toString());
				sheet.addCell(label);
				label = new Label(1, i + 1, instructors.get(i).get("loginname")
						.toString());
				sheet.addCell(label);
				label = new Label(2, i + 1, instructors.get(i).get("password")
						.toString());
				sheet.addCell(label);
				label = new Label(3, i + 1, instructors.get(i).get("userlevel")
						.toString());
				sheet.addCell(label);
				label = new Label(4, i + 1, instructors.get(i).get("score")
						+ "");
				sheet.addCell(label);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("写入Excel文件发生错误！！！");

		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
		return null;
	}

	// 批量导出单选题信息
	@RequestMapping(value = "exportDanXuanInfoExcel", method = RequestMethod.POST)
	public ModelAndView exportDanXuanInfoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.reset();
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "DanXuanInfo" + date.format(new Date()).toString();
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Content-disposition", "attachment;filename="
					+ filename + ".xls");
			WritableSheet sheet = wwb.createSheet("所有单选题信息", 0);
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 25);
			sheet.setColumnView(7, 25);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 25);

			WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
					WritableFont.BOLD, false);
			WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
			Label label = null;
			label = new Label(0, 0, "单选题等级");
			sheet.addCell(label);
			label = new Label(1, 0, "必对题");
			sheet.addCell(label);
			label = new Label(2, 0, "关键题");
			sheet.addCell(label);
			label = new Label(3, 0, "题 目");
			sheet.addCell(label);
			label = new Label(4, 0, "选项1");
			sheet.addCell(label);
			label = new Label(5, 0, "选项2");
			sheet.addCell(label);
			label = new Label(6, 0, "选项3");
			sheet.addCell(label);
			label = new Label(7, 0, "选项4");
			sheet.addCell(label);
			label = new Label(8, 0, "答 案");
			sheet.addCell(label);
			label = new Label(9, 0, "问题编号");
			sheet.addCell(label);
			List<Map> instructors = problemDanXuanService
					.getInstructorsInfoExcel();
			for (int i = 0; i < instructors.size(); i++) {
				label = new Label(0, i + 1, instructors.get(i)
						.get("problemlevel").toString());
				sheet.addCell(label);
				label = new Label(1, i + 1, instructors.get(i).get("mustright")
						.toString());
				sheet.addCell(label);
				label = new Label(2, i + 1, instructors.get(i)
						.get("keyproblem").toString());
				sheet.addCell(label);
				label = new Label(3, i + 1, instructors.get(i).get("question")
						.toString());
				sheet.addCell(label);
				label = new Label(4, i + 1, instructors.get(i).get("option1")
						.toString());
				sheet.addCell(label);
				label = new Label(5, i + 1, instructors.get(i).get("option2")
						.toString());
				sheet.addCell(label);
				label = new Label(6, i + 1, instructors.get(i).get("option3")
						.toString());
				sheet.addCell(label);
				label = new Label(7, i + 1, instructors.get(i).get("option4")
						.toString());
				sheet.addCell(label);
				label = new Label(8, i + 1, instructors.get(i).get("answer")
						.toString());
				sheet.addCell(label);
				label = new Label(9, i + 1, instructors.get(i)
						.get("problemnum").toString());
				sheet.addCell(label);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("写入Excel文件发生错误！！！");

		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
		return null;
	}

	// 批量导出多选题信息
	@RequestMapping(value = "exportDuoXuanInfoExcel", method = RequestMethod.POST)
	public ModelAndView exportDuoXuanInfoExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.reset();
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "DuoXuanInfo" + date.format(new Date()).toString();
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Content-disposition", "attachment;filename="
					+ filename + ".xls");
			WritableSheet sheet = wwb.createSheet("所有单选题信息", 0);
			sheet.setColumnView(0, 15);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 25);
			sheet.setColumnView(7, 25);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 25);

			WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
					WritableFont.BOLD, false);
			WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
			Label label = null;
			label = new Label(0, 0, "单选题等级");
			sheet.addCell(label);
			label = new Label(1, 0, "必对题");
			sheet.addCell(label);
			label = new Label(2, 0, "关键题");
			sheet.addCell(label);
			label = new Label(3, 0, "题 目");
			sheet.addCell(label);
			label = new Label(4, 0, "选项1");
			sheet.addCell(label);
			label = new Label(5, 0, "选项2");
			sheet.addCell(label);
			label = new Label(6, 0, "选项3");
			sheet.addCell(label);
			label = new Label(7, 0, "选项4");
			sheet.addCell(label);
			label = new Label(8, 0, "答 案");
			sheet.addCell(label);
			label = new Label(9, 0, "问题编号");
			sheet.addCell(label);
			List<Map> instructors = problemDuoXuanService
					.getInstructorsInfoExcel();
			for (int i = 0; i < instructors.size(); i++) {
				label = new Label(0, i + 1, instructors.get(i)
						.get("problemlevel").toString());
				sheet.addCell(label);
				label = new Label(1, i + 1, instructors.get(i).get("mustright")
						.toString());
				sheet.addCell(label);
				label = new Label(2, i + 1, instructors.get(i)
						.get("keyproblem").toString());
				sheet.addCell(label);
				label = new Label(3, i + 1, instructors.get(i).get("question")
						.toString());
				sheet.addCell(label);
				label = new Label(4, i + 1, instructors.get(i).get("option1")
						.toString());
				sheet.addCell(label);
				label = new Label(5, i + 1, instructors.get(i).get("option2")
						.toString());
				sheet.addCell(label);
				label = new Label(6, i + 1, instructors.get(i).get("option3")
						.toString());
				sheet.addCell(label);
				label = new Label(7, i + 1, instructors.get(i).get("option4")
						.toString());
				sheet.addCell(label);
				label = new Label(8, i + 1, instructors.get(i).get("answer")
						.toString());
				sheet.addCell(label);
				label = new Label(9, i + 1, instructors.get(i)
						.get("problemnum").toString());
				sheet.addCell(label);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("写入Excel文件发生错误！！！");

		} finally {
			wwb.write();
			wwb.close();
			os.flush();
			os.close();
		}
		return null;
	}
	// 批量导出判断题信息
		@RequestMapping(value = "exportPanDuanInfoExcel", method = RequestMethod.POST)
		public ModelAndView exportPanDuanInfoExcel(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			response.reset();
			OutputStream os = response.getOutputStream();
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			String filename = "PanDuanInfo" + date.format(new Date()).toString();
			try {
				response.setContentType("application/vnd.ms-excel;charset=UTF-8"); // 改成输出excel文件
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Type", "application/force-download");
				response.setHeader("Content-disposition", "attachment;filename="
						+ filename + ".xls");
				WritableSheet sheet = wwb.createSheet("所有单选题信息", 0);
				sheet.setColumnView(0, 15);
				sheet.setColumnView(1, 25);
				sheet.setColumnView(2, 25);
				sheet.setColumnView(3, 15);
				sheet.setColumnView(4, 25);
				sheet.setColumnView(5, 15);
				sheet.setColumnView(6, 25);
				sheet.setColumnView(7, 25);
				

				WritableFont blodFont = new WritableFont(WritableFont.TAHOMA, 10,
						WritableFont.BOLD, false);
				WritableCellFormat blodFormat = new WritableCellFormat(blodFont);
				Label label = null;
				label = new Label(0, 0, "单选题等级");
				sheet.addCell(label);
				label = new Label(1, 0, "必对题");
				sheet.addCell(label);
				label = new Label(2, 0, "关键题");
				sheet.addCell(label);
				label = new Label(3, 0, "题 目");
				sheet.addCell(label);
				label = new Label(4, 0, "选项1");
				sheet.addCell(label);
				label = new Label(5, 0, "选项2");
				sheet.addCell(label);				
				label = new Label(6, 0, "答 案");
				sheet.addCell(label);
				label = new Label(7, 0, "问题编号");
				sheet.addCell(label);
				List<Map> instructors = problemPanDuanService
						.getInstructorsInfoExcel();
				for (int i = 0; i < instructors.size(); i++) {
					label = new Label(0, i + 1, instructors.get(i)
							.get("problemlevel").toString());
					sheet.addCell(label);
					label = new Label(1, i + 1, instructors.get(i).get("mustright")
							.toString());
					sheet.addCell(label);
					label = new Label(2, i + 1, instructors.get(i)
							.get("keyproblem").toString());
					sheet.addCell(label);
					label = new Label(3, i + 1, instructors.get(i).get("question")
							.toString());
					sheet.addCell(label);
					label = new Label(4, i + 1, instructors.get(i).get("option1")
							.toString());
					sheet.addCell(label);
					label = new Label(5, i + 1, instructors.get(i).get("option2")
							.toString());
					sheet.addCell(label);
					label = new Label(6, i + 1, instructors.get(i).get("answer")
							.toString());
					sheet.addCell(label);
					label = new Label(7, i + 1, instructors.get(i)
							.get("problemnum").toString());
					sheet.addCell(label);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("写入Excel文件发生错误！！！");

			} finally {
				wwb.write();
				wwb.close();
				os.flush();
				os.close();
			}
			return null;
		}

}
