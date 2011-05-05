<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <base target="_self">
    <title>添加文件</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/baseinfo/user.js"></script>
	<script type="text/javascript" src="js/Validator.js"></script>
	<style type="text/css">
		.main{
			position:relative;
			width:100%;
			height:auto;
			margin: 0 auto;
		}
		.title{
			width:100%;
			height:25px;
			position:relative;
			border-bottom: 1px solid #a6c5e4;
			float:left;
			background:#e4f0f9;
			
			text-align:left;
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
			font-weight:bold;
		}
		.contents{
			margin: 0 auto;
			width:100%;
			position:relative;
			float:left;
			height:246px;
			line-height:25px !important;
		}
		.form_table{}
		.form_table tr{}
		.form_table tr td{
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
		}
		body{
		    padding:0px;
			margin:0px;
			font-size:12px;
			color:#555;
		    background: url(images/StyleBlue/manage_18.jpg) fixed repeat-x left top;
		}
		.selectTab,.unselectTab { 
		  border:1px solid #BDDFF9; 
		  border-bottom-width: 0; 
		  width:150px; 
		  height:23px; 
		  line-height:23px; 
		  vertical-align: middle; 
		  text-align:center; 
		  background-color:#37709B; 
		  margin: 0; 
		  font-weight:bold; 
		  font-size:14px; 
		  color:#FFF; 
		  cursor: pointer; 
		  float:left; 
		 } 
		 .unselectTab { 
		  color:#37709B; 
		  background-color: white; 
		 } 
		.selectContent { 
			position:relative;
			margin: 0 auto;
		    border-top: 5px #37709b solid; 
		    padding-top: 8px; 
		    clear: both; 
		    width:100%;
		 } 
		 .unselectContent { 
		     display: none; 
		 } 
	</style>
	<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSubmit(){
			var form=document.getElementById('save_form');
			if(form.fireEvent('onsubmit')){
				form.submit();
			}
			//window.close();		//关闭窗口
		}
		
    function getTabTitle(tab) { 
        var childNodesList=tab.childNodes; 
        var titleNodes=new Array(); 
        var j=0; 
        var i; 
        for (i=0;i<childNodesList.length;i++) { 
            if(childNodesList[i].nodeName=="H1") { 
                titleNodes[j]=childNodesList[i]; 
                j++; 
            } 
        } 
        return titleNodes; 
    } 
    function getTabContent(tab) { 
        var childNodesList=tab.childNodes; 
        var tabContent=new Array(); 
        var j=0; 
        var i; 
        for (i=0;i<childNodesList.length;i++) { 
            if(childNodesList[i].nodeName=="DIV") { 
                tabContent[j]=childNodesList[i]; 
                j++; 
            } 
        } 
        return tabContent; 
    } 
    function resetTab() { 
        var allDiv=document.getElementsByTagName("div"); 
        var tab=new Array(); 
        var j=0; 
        var i; 
        for (i=0;i<allDiv.length;i++) { 
            if(allDiv[i].className=="contents") { 
                tab[j]=allDiv[i]; 
                j++; 
            } 
        } 
        var tabTitle,tabContent; 
        for(i=0;i<tab.length;i++) { 
            tabTitle=getTabTitle(tab[i]); 
            tabTitle[0].className="selectTab"; 
            tabContent=getTabContent(tab[i]); 
            tabContent[0].className="selectContent"; 
            for (j=1;j<tabTitle.length;j++) { 
                tabTitle[j].className="unselectTab"; 
                tabContent[j].className="unselectContent"; 
            } 
        } 
    } 
    function changTab(tab) { 
        var tabTitle,tabContent,i; 
        if(tab.className!="selectTab") { 
            tabTitle=getTabTitle(tab.parentNode); 
            tabContent=getTabContent(tab.parentNode); 
            for(i=0;i<tabTitle.length;i++) { 
                if(tabTitle[i].className=="selectTab") { 
                    tabTitle[i].className="unselectTab"; 
                } 
                if(tabContent[i].className=="selectContent") { 
                    tabContent[i].className="unselectContent"; 
                } 
            } 
        tab.className="selectTab"; 
        for(i=0;i<tabTitle.length;i++) { 
            if(tabTitle[i].className=="selectTab") { 
                tabContent[i].className="selectContent"; 
                break; 
            } 
        } 
        } 
    } 
    function init(){
    	resetTab();
    	var params = window.dialogArguments;
    	var table = document.getElementById("edoctable");
    	var paramLength = params.length;
		if(paramLength>0){
    		var html = "";
    		for(var i=0;i<paramLength;i++){
    			var args = params[i];
    			var newTr = table.insertRow();
    			newTr.style.height="20";
    			//添加三列
				var newTd0 = newTr.insertCell();
				var newTd1 = newTr.insertCell();
				var newTd2 = newTr.insertCell();
				//设置列内容和属性
				newTd0.innerHTML = "<input name='checkItem' value='"+args[0]+"' type='checkbox'></input>";
				newTd1.innerHTML= args[1];
				newTd2.innerHTML= "<a href='javascript:void(0)' onclick='deleteRow(this)'>删除</a>";
    		}
    	}
    }
    function changeScope(isOuther){
    	var selectUserBt = document.getElementById("selectUserBt");
    	var user_div = document.getElementById("user_div");
    	if(isOuther==0){
    		selectUserBt.disabled = true;
    		user_div.style.display = "none";
    	}else if(isOuther==1){
    		selectUserBt.disabled = false;
    		user_div.style.display = "block";
    	}
    }
    //删除共享文件
    function deleteRow(obj){
    	obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
    }
    
    function closeWin(){
    	window.close();
    }
    
    function save(){
    	
    	var edocTable = document.getElementById("edoctable");
    	var edocTableRows = edocTable.rows.length;
    	if(edocTableRows<2){
    		alert("请选择共享的文件！");
    		return;
    	}
    	
    	var userTable = document.getElementById("userTable");
    	var userTableRows = userTable.rows.length;
    	if(userTableRows<2){
    		alert("请选择共享的用户！");
    		return;
    	}
    }
    
    function selectUser(){
    	var  returnValue = showModalDialog("../baseinfo/select_users.jsp", "", "dialogWidth:800px; dialogHeight:500px; status:0; help:0");
    	
    	if(returnValue!=null && returnValue.length>0){
    		var table = document.getElementById("userTable");
    		var html = "";
    		var paramLength = returnValue.length;
    		for(var i=0;i<paramLength;i++){
    			var args = returnValue[i];
    			var newTr = table.insertRow();
    			//添加三列
				var newTd0 = newTr.insertCell();
				var newTd1 = newTr.insertCell();
				//设置列内容和属性
				newTd0.innerHTML= args[1];
				newTd1.innerHTML= "<a href='javascript:void(0)' onclick='deleteRow(this)'>删除</a>";
    		}
    	}
	
    }
	</script>
  </head>
  
  <body onload="init()">
    <div class="main">
    	<div class="win_toolbar">
    		<input style="line-height:13px;" type="button" onclick="save()" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;<input style="line-height:13px;" type="button" onclick="closeWin()" value="关闭"/>
    	</div>
    	<form id="save_form" action="fileAction!uploadFiles.action" onsubmit="return Validator.Validate(this,3)" method="post" enctype="multipart/form-data">
    	<div class="contents">
    		<h1 onclick="changTab(this)">文件信息</h1> 
		    <h1 onclick="changTab(this)">共享用户</h1> 
		    <div>
		    	<table id="edoctable" class="edoctable">
						<tr>
							<th width="20px"><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								名称
							</th>
							<th width="50px">
								操作
							</th>
						</tr>
				</table>
		    </div> 
		    <div>
		    	<div style="margin: 0 auto;width:80%;height:30px;">
		    		<table width="50%" align="center">
						<tr>
							<td width="25%"><input type="radio" name="fruit" value = "Apple" onclick="changeScope(0)" checked>所有人</td>
							<td width="25%"><input type="radio" name="fruit" value = "Apple" onclick="changeScope(0)">本部门</td>
							<td width="50%"><input type="radio" name="fruit" value = "Apple" onclick="changeScope(1)">其他&nbsp;&nbsp;&nbsp;&nbsp;<input id="selectUserBt" onclick="selectUser()" type="button" value="选择人员" disabled/> </td>
						</tr>
					</table>
		    	</div>
		    	<div id="user_div" style="margin: 0 auto;display:none">
		    		<table id="userTable" class="edoctable">
						<tr>
							<th>
								名称
							</th>
							<th width="50px">
								操作
							</th>
						</tr>
				</table>
		    	</div>
		    </div> 
    	</div>
    	</form>
    </div>
  </body>
</html>
