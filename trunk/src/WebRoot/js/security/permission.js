//添加菜单
function add_permission(){
	showModalDialog("jsp/security/save_menu_win.jsp", "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
}

//修改菜单
function edit_permission(){
	alert('修改');
}

//删除菜单
function delete_permission(){
	alert('删除');
}

//选择上级菜单
function select_permission(){
	var  returnValue = showModalDialog("../../jsp/security/select_menu_win.jsp", "", "dialogWidth:450px; dialogHeight:600px; status:0; help:0");
	
	if(returnValue!=null && returnValue.length>0){
		document.getElementById('parentMenuText').value = returnValue[1];
		document.getElementById('parentMenuId').value = returnValue[0];
	}
}
