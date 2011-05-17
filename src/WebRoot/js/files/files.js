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
	//showModalDialog("jsp/files/uploadfile_win.jsp", "", "dialogWidth:800px; dialogHeight:400px; status:0; help:0");
	var params = [];
	params[0] = document.getElementById('parentId').value;
	showModalDialog("jsp/files/addfile_win.jsp?Rnd="+Math.random(), params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
	doSearch();
}

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
	var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
	var form = document.getElementById("queryForm1");
	form.action = "fileAction!deleteFile.action?deleteParams="+deleteParam+"&parentId="+parentId;
	form.submit();
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
		var form = document.getElementById("queryForm1");
		form.action = "fileAction!deleteFile.action?parentId="+parentId+param;
		form.submit();
	}else{
		alert('请选择要删除的记录!');
	}
}


