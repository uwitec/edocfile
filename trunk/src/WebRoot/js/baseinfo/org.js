function showAddOrgWin(){
	showModalDialog("jsp/baseinfo/save_org_win.jsp", "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
	window.location.reload();
}

//删除一条记录
function deleteOneOrg(orgId){
	
}

function show_select_org_win(){
	var  returnValue = showModalDialog("select_org_win.jsp", "", "dialogWidth:450px; dialogHeight:600px; status:0; help:0");
	
	if(returnValue!=null && returnValue.length>0){
		document.getElementById('parentOrgName').value = returnValue[1];
		document.getElementById('parentOrgId').value = returnValue[0];
	}
}