var instructor_currentSearchFilter = "";
var instructorinfowin;
var instructorimportwin;

var seuser = new Ext.data.Record.create([ {
	name : 'id'
}, {
	name : 'problemlevel'
}, {
	name : 'mustright'
}, {
	name : 'keyproblem'
}, {
	name : 'question'
}, {
	name : 'option1'
}, {
	name : 'option2'
}, {
	name : 'option3'
}, {
	name : 'option4'
}, {
	name : 'answer'
}, {
	name : 'problemnum'
}

]);

var emptysecrecy = new seuser({
	id : '',
	problemlevel : '',
	mustright : '',
	keyproblem : '',
	question : '',
	option1 : '',
	option2 : '',
	option3 : '',
	option4 : '',
	answer : '',
	problemnum : ''
});

var instructorstore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		totalProperty : 'totalProperty',
		root : 'root'
	}, seuser),
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : ProblemDanXuanController.paginationShow
	})
});


var instructorfields = [ {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'hidden',
		name : 'id',
		anchor : '95%'
	}, {
		xtype : 'textfield',
		id : 'problemlevel',
		fieldLabel : '单选题等级',
		name : 'problemlevel',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'mustright',
		fieldLabel : '必对题',
		name : 'mustright',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		fieldLabel : '关键题',
		id : 'keyproblem',
		name : 'keyproblem',
		anchor : '97.5%',
		//maxLength : 55,
		allowBlank : false
	} ]
}, {
	columnWidth : .9,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'question',
		fieldLabel : '题   目',
		name : 'question',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .9,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'option1',
		fieldLabel : '选 项 1',
		name : 'option1',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .9,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'option2',
		fieldLabel : '选 项 2',
		name : 'option2',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .9,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'option3',
		fieldLabel : '选 项 3',
		name : 'option3',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .9,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'option4',
		fieldLabel : '选 项 4',
		name : 'option4',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'answer',
		fieldLabel : '答  案',
		name : 'answer',
		anchor : '97.5%'
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'problemnum',
		fieldLabel : '问题编号',
		name : 'problemnum',
		anchor : '97.5%'
	} ]
} ];

var college_instructorbasicform = new Ext.FormPanel({
	id : 'college_instructorbasicform',
	width : 600,
	labelWidth : 65,
	region : 'center',
	labelAlign : 'left',
	layout : 'column',
	items : [ instructorfields ]
});


var college_instructorform = new Ext.Panel({
	id : 'college_studentform',
	title : '单选题基本信息',
	labelWidth : 80,
	height : 400,
	autoScroll : true,
	frame : true,
	bodyStyle : 'padding:5',
	labelAlign : 'left',
	region : 'south',
	layout : 'border',
	buttonAlign : 'center',
	items : [college_instructorbasicform ],
	buttons : [ {
		text : '保存',
		handler : function() {
			enableOfInstructorbasicform();
			Ext.MessageBox.wait("正在执行...", "提示");
			if (college_instructorbasicform.getForm().isValid()) {
				var seuser = {
					id : '',
					problemlevel : '',
					mustright : '',
					keyproblem : '',
					question : '',
					option1 : '',
					option2 : '',
					option3 : '',
					option4 : '',
					answer : '',
					problemnum : ''
				};

				enableOfInstructorbasicform();
				seuser = college_instructorbasicform.getForm().getValues();
				// stunum = Ext.getCmp("studentnum").getValue();
				ProblemDanXuanController.updateInstructorAllInfo(seuser, function(data) {
					if (data) {
						Ext.MessageBox.alert('提示', "保存成功！");
					} else {
						Ext.MessageBox.alert('提示', "保存失败！");
					}
					// readOnlyOfStudentbasicform();
				});
			} else {
				Ext.MessageBox.alert('提示', "输入信息有误！");
			}

		}
	}, {
		text : '清空',
		id : 'clearstuinfo',
		handler : function() {
			enableOfInstructorbasicform();
			college_instructorbasicform.getForm().reset();
			college_instructorbasicform.getForm().reset();
		}
	} ]
});

function enableOfInstructorbasicform() {
	Ext.getCmp("problemlevel").setDisabled(false);
	// Ext.getCmp("id").setDisabled(false);
	Ext.getCmp("mustright").setDisabled(false);
	Ext.getCmp("keyproblem").setDisabled(false);
	Ext.getCmp("question").setDisabled(false);
	Ext.getCmp("option1").setDisabled(false);
	Ext.getCmp("option2").setDisabled(false);
	Ext.getCmp("option3").setDisabled(false);
	Ext.getCmp("option4").setDisabled(false);
	Ext.getCmp("answer").setDisabled(false);
	Ext.getCmp("problemnum").setDisabled(false);
}

var instructor_searchform = new Ext.form.FormPanel({
	id : 'instructor_searchform',
	region : 'center',
	// autoScroll:true,
	// bodyStyle:"overflow-y:scroll;overflow-x:hidden",
	frame : true,
	labelWidth : 68,
	autoHeight : true,
	items : [ {
		// xtype:'fieldset',
		title : '单选题信息',
		autoHeight : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '单选题等级',
				name : 'and$problemlevel$=$value',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '必对题',
				name : 'and$mustright$=$value',
				anchor : '96%'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '关键题',
				name : 'and$keyproblem$=$value',
				anchor : '96%'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '题  目',
				name : 'and$question$like$%value%',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '答  案',
				name : 'and$answer$=$value',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '问题编号',
				name : 'and$problem$=$value',
				anchor : '96%'
			} ]
		} ]
	} ],
	keys : [ {
		key : 13,// 回车
		fn : function() {
			instructor_search();
			instructor_searchWin.hide();
		}
	} ]
});

var instructor_searchWin = new Ext.Window({
	title : '查询条件',
	width : 500,
	autoHeight : true,
	minWidth : 300,
	minHeight : 200,
	closeAction : 'hide',
	layout : 'fit',
	plain : true,
	buttonAlign : 'center',
	items : [ instructor_searchform ],
	modal : true,
	shadow : true,
	buttons : [ {
		text : '查询',
		handler : function() {
			instructor_search();
			instructor_searchWin.hide();
		}
	}, {
		text : '清空',
		handler : function() {
			instructor_searchform.getForm().reset();
			instructor_currentSearchFilter = "";
		}
	}, {
		text : '关闭',
		handler : function() {
			instructor_searchWin.hide();
		}
	} ]
});

var instructorgrid = new Ext.grid.GridPanel({
	region : 'center',
	id : 'instructorgrid',
	store : instructorstore,
	loadMask : true,
	width : 5000,
	stripeRows : true,
	autoScroll : true,
	viewConfig : {
		sortDescText : '降序',
		sortAscText : '升序',
		columnsText : '显示列',
		forceFit : false
	},
	columns : [ new Ext.grid.RowNumberer({
		width : 28
	}), {
		id : 'problemlevel',
		header : '单选题等级',
		dataIndex : 'problemlevel',
		width : 80,
		sortable : true
	}, {
		id : 'mustright',
		header : '必对题',
		dataIndex : 'mustright',
		width : 80,
		sortable : true
	}, {
		id : 'keyproblem',
		header : '关 键 题',
		dataIndex : 'keyproblem',
		width : 80,
		sortable : true
	}, {
		id : 'question',
		header : '题   目',
		dataIndex : 'question',
		width : 80,
		sortable : true
	}, {
		id : 'option1',
		header : '选  项  1',
		dataIndex : 'option1',
		width : 150,
		sortable : true
	}, {
		id : 'option2',
		header : '选  项  2',
		dataIndex : 'option2',
		width : 150,
		sortable : true
	}, {
		id : 'option3',
		header : '选  项  3',
		dataIndex : 'option3',
		width : 150,
		sortable : true
	}, {
		id : 'option4',
		header : '选  项  4',
		dataIndex : 'option4',
		width : 150,
		sortable : true
	}, {
		id : 'answer',
		header : '答   案',
		dataIndex : 'answer',
		width : 80,
		sortable : true
	}, {
		id : 'problemnum',
		header : '问题编号',
		dataIndex : 'problemnum',
		width : 80,
		sortable : true
	} ],
	tbar : [
			{
				text : '批量导入单选题信息',
				iconCls : 'import',
				scope : this,
				handler : function() {
					instructorimport().show();
				}
			},
			'-',
			{
				text : '新增',
				iconCls : 'add',
				scope : this,
				handler : function() {
					enableOfInstructorbasicform();
					college_instructorbasicform.getForm().loadRecord(
							emptysecrecy);
					instructorInfoWinInit("新增单选题信息").show();
				}
			},
			'-',
			{
				text : '编辑',
				iconCls : 'edit',
				scope : this,
				handler : function() {
					instructorgrid.fireEvent('rowdblclick', instructorgrid);
				}
			},
			'-',
			{
				text : '删除',
				iconCls : 'remove',
				scope : this,
				handler : function() {
					if (instructorgrid.getSelectionModel().hasSelection()) {
						Ext.MessageBox.confirm('提示', '你确定要删除选中的单选题?', function(
								button) {
							if (button == 'yes') {
								var list = instructorgrid.getSelectionModel()
										.getSelections();
								var rList = [];
								for (var i = 0; i < list.length; i++) {
									rList[i] = list[i].data["id"];
								}

								ProblemDanXuanController.deleteInstructors(rList,
										function(data) {
											if (data) {
												Ext.MessageBox.alert('提示',
														'删除单选题成功!');
												instructorGridInit();
											} else {
												Ext.MessageBox.alert('提示',
														"删除单选题失败!");
											}
										});
							}
						});
					} else
						Ext.MessageBox.alert('提示', "请至少选择一条记录!");
				}
			}, '-', {
				text : '查询',
				iconCls : 'upload-icon',
				scope : this,
				handler : function() {
					instructor_searchWin.show();
					instructor_searchform.getForm().reset();
				}
			}, '-', {
				text : '导出单选题信息',
				iconCls : 'import',
				scope : this,
				handler : function() {
					exportInstructorsInfoExcel();
				}
			}

	],
	bbar : new Ext.PagingToolbar({
		pageSize : 20,
		store : instructorstore,
		displayInfo : true,
		firstText : '首页',
		lastText : '尾页',
		prevText : '上一页',
		nextText : '下一页',
		refreshText : '刷新',
		displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg : "没有记录",
		doLoad : function(start) {
			var params = {};
			params.filter = instructor_currentSearchFilter;
			params.start = start;
			params.limit = this.pageSize;
			if (this.fireEvent("beforechange", this, params) !== false) {
				this.store.load({
					params : params
				});
			}
		}
	}),
	listeners : {
		rowdblclick : function(grid) {
			enableOfInstructorbasicform();
			if (grid.getSelectionModel().hasSelection()) {
				instructorInfoWinInit("编辑单选题信息").show();
				college_instructorbasicform.getForm().setValues(
						grid.getSelectionModel().getSelected().data);
			} else {
				Ext.MessageBox.alert('提示', "请选择一条信息进行编辑!");
			}

		}
	}
});

var instructorimportform = new Ext.form.FormPanel({
	labelAlign : 'left',
	labelWidth : 60,
	region : 'center',
	buttonAlign : 'center',
	frame : true,
	url : '../xlsToJsonAboutDanXuan.do',// fileUploadServlet
	width : 300,
	height : 200,
	fileUpload : true,

	items : [ {
		xtype : 'textfield',
		fieldLabel : '文件名',
		name : 'file',
		inputType : 'file'// 文件类型
	} ]
});

function instructorimport() {
	if (!instructorimportwin) {
		instructorimportwin = new Ext.Window(
				{
					title : '文件上传',
					width : 480,
					height : 360,
					minWidth : 400,
					minHeight : 360,
					closeAction : 'hide',
					layout : 'border',
					plain : true,
					bodyStyle : 'padding:5px;',
					buttonAlign : 'center',
					items : instructorimportform,
					buttons : [ {
						text : '上传',
						handler : function() {
							Ext.MessageBox.wait('正在检查数据,请等待...', '提示');
							instructorimportform
									.getForm()
									.submit(
											{
												success : function(form, action) {
													var jsonData = Ext.util.JSON
															.decode(action.response.responseText);
													if (jsonData.success == true) {
														Ext.MessageBox
																.alert(
																		'提示',
																		jsonData.errors.info);
														instructorGridInit();
													} else {
														Ext.MessageBox
																.alert(
																		'提示',
																		jsonData.errors.info);
													}
												},
												failure : function(form, action) {
													jsonData = Ext.util.JSON
															.decode(action.response.responseText);
													Ext.Msg
															.alert(
																	'文件上传失败',
																	jsonData.errors.info,
																	function() {
																		instructorimportwin
																				.hide();
																	});
												}
											});
						}
					} ]
				});
	}
	return instructorimportwin;
}

function exportInstructorsInfoExcel() {
	var f = document.getElementById('exportInstructorsInfoExcel');
	f.action = '../exportDanXuanInfoExcel.do';
	f
			.submit({
				failure : function(form, action) {
					var error = Ext.util.JSON
							.decode(action.response.responseText).error;
					Ext.MessageBox.alert('导出失败', "原因：" + error);
				}

			});
}

function instructor_search() {
	instructor_currentSearchFilter = Ext.encode(instructor_searchform.getForm()
			.getFieldValues());
	instructorstore.load({
		params : {
			filter : instructor_currentSearchFilter,
			start : 0,
			limit : 20
		}
	});
}

function instructorInfoWinInit(title) {
	if (!instructorinfowin) {
		instructorinfowin = new Ext.Window({
			title : '',
			width : 1000,
			height : 400,
			closeAction : 'hide',
			layout : 'fit',
			plain : true,
			bodyStyle : 'padding:5px',
			items : [ college_instructorform ]
		});
	}
	instructorinfowin.setTitle(title);
	return instructorinfowin;
}

function instructorGridInit() {
	instructorstore.load({
		params : {
			filter : instructor_currentSearchFilter,
			start : 0,
			limit : 20
		}
	});
}

function studentInfoManagerPageInit() {
	new Ext.Viewport({
		layout : 'border',
		hideMode : Ext.isIE ? 'offsets' : 'display',
		items : [ instructorgrid ],
		renderTo : Ext.getBody()
	});
	instructorGridInit();

}

function tabAdd(tabId, script, tabTitle) {
	tabPanel = window.top.tabPanel;
	if (tabPanel.findById(tabId) == null) {
		tabPanel
				.add({
					id : tabId,
					title : tabTitle,
					height : 500,
					autoScroll : true,
					autoWidth : true,
					closable : true,
					frame : true,
					html : '<iframe id="'
							+ tabId
							+ '_Frame" src="'
							+ script
							+ '" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>',
					listeners : {
						activate : function() {
							this.getUpdater().refresh();
						},
						beforeclose : function() {
							var frame = window.top.document
									.getElementById(this.id + '_Frame');
							if (frame.scr != null)
								frame.src = "javascript:false";
						}
					}
				});
	}
	window.top.viewport.doLayout(true, true);
	tabPanel.show();
	tabPanel.setActiveTab(tabId);
};

