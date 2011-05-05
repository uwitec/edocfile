
function showAddUserWin(){
	var orgId = document.getElementById('orgId').value;
	showModalDialog("save_user_win.jsp", "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
	parent.frames("userlist_users").location.reload();
}

function show_select_org_win(){
	var  returnValue = showModalDialog("select_org_win.jsp", "", "dialogWidth:450px; dialogHeight:600px; status:0; help:0");
	
	if(returnValue!=null && returnValue.length>0){
		document.getElementById('orgName').value = returnValue[1];
		document.getElementById('orgId').value = returnValue[0];
	}
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

function openPage(currentPage){
	document.getElementById("currentPage_param").value=currentPage;
	doSearch();
}
//查询
function doSearch(){
	var form = document.getElementById("queryForm");
	form.action = "userAction!getUsersByOrgId.action";
	form.submit();
}


function deleteOne(deleteParam){
	var form = document.getElementById("queryForm");
	form.action = "userAction!deleteUser.action?deleteParams="+deleteParam+"&Rnd="+Math.random();
	form.submit();
}

//删除单条记录,url里面必须包含一个参数,例如：userAction!deleteUser.action?invalidate=1
function deleteOneAction(deleteParam,url){
	var form = document.getElementById("queryForm");
	form.action = url+"&deleteParams="+deleteParam;
	form.submit();
}


//删除多条记录,url里面必须包含一个参数,例如：userAction!deleteUser.action?invalidate=1
function deleteMoreAction(url){
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
		form.action = url+param;
		form.submit();
	}else{
		alert('请选择要删除的记录!');
	}
}


function updateUser(id){
	var orgId = document.getElementById('orgId').value;
	showModalDialog("userAction!getUserById.action?userId="+id+"&forward=toEditUser&Rnd="+Math.random(), "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
	parent.frames("userlist_users").location.reload();
}
