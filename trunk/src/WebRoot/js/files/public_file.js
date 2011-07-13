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

//撤销共享文件操作
function cancelShore(id){		
	var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
	var form = document.getElementById("queryForm1");
	form.action = "shoreFileAction!cancelShore.action?fileId="+id+"&Rnd="+Math.random();
	form.submit();
}

//文档预览
function previewFile(id,version){
	window.open("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(),"","resizable=yes,status=no,toolbar=no,menubar=no,location=no");
}

//查看文件详细信息
function showFileInfo(id,fileName){
	var params = [];
	params[0] = fileName;
	showModalDialog("fileAction!getFileInfo.action?sourceFileId="+id+"&Rnd="+Math.random(), params, "dialogWidth:1000px; dialogHeight:600px;help:no;scroll:no;status:no");
}

//翻页操作
function openPage(currentPage){
	document.getElementById("currentPage_param").value=currentPage;
	searchFile();
}

//查询共享文件夹
function searchFile(){
	var form = document.getElementById("queryForm1");
	form.action = "shoreFileAction!getShoredFiles.action?Rnd="+Math.random();
	form.submit();
}

//展示方式的改变
function changeLayout(type){
	document.getElementById('layoutStyle').value=type;	
	
	var form = document.getElementById("queryForm1");
	form.action = "shoreFileAction!getShoredFiles.action?Rnd="+Math.random();
	form.submit();
}

function openFolder(parentId){
	document.getElementById('parentId').value=parentId;
	var form = document.getElementById("queryForm1");
	form.action = "shoreFileAction!getShoredFiles.action?Rnd="+Math.random();
	form.submit();
}
//重新加载页面
function reloadPage(){
	var form = document.getElementById("queryForm1");
	form.action = "shoreFileAction!getShoredFiles.action?Rnd="+Math.random();
	form.submit();
}