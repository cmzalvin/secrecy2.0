var instructor_currentSearchFilter = "";
var instructorinfowin;
var instructorimportwin;

var seuser = new Ext.data.Record.create([ {
	name : 'id'
}, {
	name : 'name'
}, {
	name : 'loginname'
}, {
	name : 'password'
}, {
	name : 'userlevel'
}, {
	name : 'score'
} ]);

var emptysecrecy = new seuser({
	id : '',
	name : '',
	loginname : '',
	password : '',
	userlevel : '',
	score : ''
});

var instructorstore = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
		totalProperty : 'totalProperty',
		root : 'root'
	}, seuser),
	proxy : new Ext.ux.data.DWRProxy({
		dwrFunction : UserController.paginationShow
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
		id : 'name',
		fieldLabel : '姓  名',
		name : 'name',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'loginname',
		fieldLabel : '登录用户',
		name : 'loginname',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .4,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		fieldLabel : '密码',
		id : 'password',
		name : 'password',
		anchor : '97.5%',
		maxLength : 55,
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'userlevel',
		fieldLabel : '等   级',
		name : 'userlevel',
		anchor : '97.5%',
		allowBlank : false
	} ]
}, {
	columnWidth : .3,
	layout : 'form',
	items : [ {
		xtype : 'textfield',
		id : 'score',
		fieldLabel : '分数',
		name : 'score',
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
	title : '保密员基本信息',
	labelWidth : 80,
	height : 400,
	autoScroll : true,
	frame : true,
	bodyStyle : 'padding:5',
	labelAlign : 'left',
	region : 'south',
	layout : 'border',
	buttonAlign : 'center',
	items : [ college_instructorbasicform ],
	buttons : [ {
		text : '保存',
		handler : function() {
			enableOfInstructorbasicform();
			Ext.MessageBox.wait("正在执行...", "提示");
			if (college_instructorbasicform.getForm().isValid()) {
				var seuser = {
					id : '',
					name : '',
					loginname : '',
					password : '',
					userlevel : '',
					score : ''
				};

				enableOfInstructorbasicform();
				seuser = college_instructorbasicform.getForm().getValues();
				UserController.updateInstructorAllInfo(seuser, function(data) {
					if (data) {
						Ext.MessageBox.alert('提示', "保存成功！");
					} else {
						Ext.MessageBox.alert('提示', "保存失败！");
					}
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
	Ext.getCmp("name").setDisabled(false);
	Ext.getCmp("loginname").setDisabled(false);
	Ext.getCmp("password").setDisabled(false);
	Ext.getCmp("userlevel").setDisabled(false);
	Ext.getCmp("score").setDisabled(false);
}

var instructor_searchform = new Ext.form.FormPanel({
	id : 'instructor_searchform',
	region : 'center',
	frame : true,
	labelWidth : 68,
	autoHeight : true,
	items : [ {
		// xtype:'fieldset',
		title : '保密员信息',
		autoHeight : true,
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '姓名',
				name : 'and$name$like$%value%',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '登录用户',
				name : 'and$loginname$=$value',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '等级',
				name : 'and$userlevel$=$value',
				anchor : '96%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '分数',
				name : 'and$score$=$value',
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
		id : 'name',
		header : '姓名',
		dataIndex : 'name',
		width : 100,
		sortable : true
	}, {
		id : 'loginname',
		header : '登录用户',
		dataIndex : 'loginname',
		width : 100,
		sortable : true
	}, {
		id : 'userlevel',
		header : '等级',
		dataIndex : 'userlevel',
		width : 150,
		sortable : true
	}, {
		id : 'score',
		header : '分数',
		dataIndex : 'score',
		width : 120,
		sortable : true
	} ],
	tbar : [
			{
				text : '批量导入保密员信息',
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
					instructorInfoWinInit("新增保密员信息").show();
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
						Ext.MessageBox.confirm('提示', '你确定要删除选中的保密员?', function(
								button) {
							if (button == 'yes') {
								var list = instructorgrid.getSelectionModel()
										.getSelections();
								var rList = [];
								for (var i = 0; i < list.length; i++) {
									rList[i] = list[i].data["id"];
								}

								UserController.deleteInstructors(rList,
										function(data) {
											if (data) {
												Ext.MessageBox.alert('提示',
														'删除保密员成功!');
												instructorGridInit();
											} else {
												Ext.MessageBox.alert('提示',
														"删除保密员失败!");
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
				text : '导出保密员信息',
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
				instructorInfoWinInit("编辑保密员信息").show();
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
	url : '../xlsToJsonAboutInstructor.do',// fileUploadServlet
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
	f.action = '../exportInstructorsInfoExcel.do';
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
			width : 800,
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

