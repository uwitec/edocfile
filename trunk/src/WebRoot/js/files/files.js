//查询
function doSearch(){
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
	form.submit();
}

//显示创建目录窗口
function showAddFolderWin(){
	var params = [];
	params[0] = document.getElementById('parentId').value;
	showModalDialog("jsp/files/save_folder_win.jsp?Rnd="+Math.random(), params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
	doSearch();
}

//显示上传文件窗口
function showUploadWin(){
	var params = [];
	params[0] = document.getElementById('parentId').value;
	showModalDialog("jsp/files/addfile_win.jsp?Rnd="+Math.random(), params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
	doSearch();
}

//复选框选中/取消事件
function selectAllCheckbox(){
	var flag = document.getElementById("selectAll");
	var array = document.getElementsByName("checkItem");
	var length = array.length;
	if (flag.checked == true) {
		for (var i = 0; i < length; i++) {
			array[i].checked = true;
		}
	} else {
		for (var i = 0; i < length; i++) {
			array[i].checked = false;
		}
	}
}

//翻页操作
function openPage(currentPage){
	document.getElementById("currentPage_param").value=currentPage;
	doSearch();
}

//删除单条记录
function deleteOne(deleteParam){
	if(confirm('确认需要删除选定的记录?')){
		var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
		var form = document.getElementById("queryForm1");
		form.action = "fileAction!deleteFile.action?deleteParams="+deleteParam+"&parentId="+parentId;
		form.submit();
	}else{
		return;
	}
}

//删除多条记录
function deleteMore(){
	var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
	var array = document.getElementsByName("checkItem");
	var length = array.length;
	var param = "";
	var selectFlag = false;
	for (var i = 0; i < length; i++) {
		if (array[i].checked == true) {
			selectFlag = true;
			param +="&deleteParams="+array[i].value;
		}
	}
	if(selectFlag){
		if(confirm('确认需要删除选定的记录?')){
			var form = document.getElementById("queryForm1");
			form.action = "fileAction!deleteFile.action?parentId="+parentId+param;
			form.submit();
		}else{
			return;
		}
	}else{
		alert('请选择要删除的记录!');
	}
}

//共享文件(文件夹)
function showShoreFileWin(id,isShored){
	var args = [];
	
	args[0] = id;
	var fileName = document.getElementById("sourceFileName"+id).value;
	args[1] = fileName;
	var parentId = document.getElementById('parentId').value;
	args[2] = parentId;
	
	var params = [];
	params[0] = args;
		
	var flag = showModalDialog("shoreFileAction!beforeShoreFile.action?sourceFileId="+id+"&Rnd="+Math.random(), params, "dialogWidth:900px; dialogHeight:550px;help:no;scroll:no;status:no");
	if(flag==true){
		alert('共享设置已完成!');
		var form = document.getElementById("queryForm1");
		form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
		form.submit();
	}
}

//显示文件基本信息
function showFileInfo(id,fileName){
	var params = [];
	params[0] = fileName;
	showModalDialog("fileAction!getFileInfo.action?sourceFileId="+id+"&Rnd="+Math.random(), params, "dialogWidth:1100px; dialogHeight:600px;help:no;scroll:no;status:no");
}

//显示文件版本信息
function showVersion(id,fileName){
	var params = [];
	params[0] = fileName;
	showModalDialog("fileAction!getEdocFileVersions.action?sourceFileId="+id+"&Rnd="+Math.random(), params, "dialogWidth:1100px; dialogHeight:500px;help:no;scroll:no;status:no");
}

//文档预览
function previewFile(id,version){
	window.open("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(),"","resizable=yes,status=no,toolbar=no,menubar=no,location=no");

	//重新刷新页面
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
	form.submit();
}

//文件查找
function searchFile(){
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
	form.submit();
}

//撤销共享文件操作
function cancelShore(id){		
	var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!cancelShore.action?fileId="+id+"&page=myFolder&Rnd="+Math.random();
	form.submit();
}

//展示方式的改变
function changeLayout(type){
	document.getElementById('layoutStyle').value=type;	
	
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
	form.submit();
}

//打开文件夹操作
function openFolder(parentId){
	document.getElementById('parentId').value=parentId;
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
	form.submit();
}



