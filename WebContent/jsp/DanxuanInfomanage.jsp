<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/Ext.ux.form.FileUploadField.css" />
<link rel="stylesheet" type="text/css"
	href="/SecrecySystem/resources/awesomeuploader/AwesomeUploader.css" />
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/ext/ext-all.js"></script>
<script type='text/javascript' src='/SecrecySystem/dwr/engine.js'></script>
<script type='text/javascript' src='/SecrecySystem/dwr/util.js'></script>
<script type='text/javascript'
	src='/SecrecySystem/dwr/interface/ProblemDanXuanController.js'></script>


<script type="text/javascript"
	src="/SecrecySystem/resources/custom/DwrProxy.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/Ext.ux.form.FileUploadField.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/Ext.ux.XHRUpload.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/swfupload.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/swfupload.swfobject.js"></script>
<script type="text/javascript"
	src="/SecrecySystem/resources/awesomeuploader/AwesomeUploader.js"></script>


<script type="text/javascript"
	src="/SecrecySystem/resources/js/DanxuanInfoManage.js"></script>

<style type="text/css">
.icon-grid {
	background-image: url(../resources/icons/grid.gif) !important;
}

.add {
	background-image: url(../resources/icons/add.gif) !important;
}

.edit {
	background-image: url(../resources/icons/edit.gif) !important;
}

.remove {
	background-image: url(../resources/icons/delete.gif) !important;
}

.save {
	background-image: url(../resources/icons/save.gif) !important;
}

.back {
	background-image: url(../resources/icons/back.gif) !important;
}

.refresh {
	background-image: url(../resources/icons/refresh.gif) !important;
}

.print {
	background-image: url(../resources/icons/printer.gif) !important;
}

.import {
	background-image: url(../resources/icons/landDataImport.png) !important;
}

.upload-icon {
	background: url(../resources/icons/image_add.png) no-repeat 0 0
		!important;
}

.dIcon {
	background-image: url(../resources/icons/data.gif) !important;
}

.lIcon {
	background-image: url(../images/database_table.png) !important;
}

.pIcon {
	background-image: url(../images/house.png) !important;
}
</style>

</head>
<body>

	<div style="display: none">
		<form name="exportInstructorsInfoExcel"
			id="exportInstructorsInfoExcel" action="" target="export"
			method="post" accept-charset="UTF-8"></form>
		<iframe name="export" id="export"></iframe>
	</div>
	<script language="javascript">
		Ext.onReady(function() {
			Ext.QuickTips.init();
			this.studentInfoManagerPageInit();
		});
	</script>
</body>

</html>