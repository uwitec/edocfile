<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<base target="_self">
		<title>共享文件</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript" src="js/Validator.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.font_red{
			    color:#B9090C;
			}
		</style>
		<script type="text/javascript">
    function init(){
    	var params = window.dialogArguments;
    	document.getElementById("sourceFileNameTd").innerHTML = "<strong><font color=\"blue\">"+params[0][1]+"</font></strong>";
    	document.getElementById("sourceFileId").value = params[0][0];
    	document.getElementById("parentId").value = params[0][2];
    }
    function changeScope(isOuther){
    	var selectUserBt = document.getElementById("selectUserBt");
    	var user_div = document.getElementById("user_div");
    	if(isOuther==0){
    		selectUserBt.disabled = true;
    		//user_div.style.display = "none";
    	}else if(isOuther==1){
    		selectUserBt.disabled = false;
    		//user_div.style.display = "block";
    	}
    }
    function closeWin(){
    	window.close();
    }
    
    function onSubmit(){
    	
		document.getElementById("save_form").submit();
    	window.returnValue=true;
        window.close();
    }
    
    function selectUser(){
    	var  returnValue = showModalDialog("jsp/baseinfo/select_users.jsp", "", "dialogWidth:800px; dialogHeight:500px; status:0; help:0");
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
				var newTd2 = newTr.insertCell();
				
				//设置列内容和属性
				newTd0.innerHTML= "<input name='visitUserIds' value='"+args[0]+"' type='hidden'/><input type='hidden' name='visitUserInfoId_"+args[0]+"'/><input type='hidden' name='visitUserName_"+args[0]+"' value='"+args[1]+"' />"+args[1];
				newTd1.innerHTML= "<input type='checkbox' name='permission_"+args[0]+"' value='view' checked>预览&nbsp;&nbsp;<input type='checkbox' name='permission_"+args[0]+"' value='edit'>编辑&nbsp;&nbsp;<input type='checkbox' name='permission_"+args[0]+"' value='downLoad'>下载";
				newTd2.innerHTML= "<a href=\"javascript:void(0)\" onclick=\"deleteRow(this,'')\">删除</a>";
    		}
    	}	
    }
    
     //删除共享文件
    function deleteRow(obj,visitUserInfoId){
    	if(!visitUserInfoId){
    		obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
    		alert('删除成功!');
    	}else{
    		$.ajax({
				    url:'visitUserAction!deleteVisitUserInfo.action?visitUserInfoId='+visitUserInfoId+"&Rnd="+Math.random(), 
				    type:'post',
				    error: function(){
				    	return false;
				    },
				    success: function(json){
				    	obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
    					alert('删除成功!');
				    }
			   	});
    	}
    }
    //设置消息发送类型
    function setSendMsgType(type){
    	if(type==1){
    		var checked = document.getElementById('sysMsgType').checked;
    		var sendMsgFlag = document.getElementById('sendMsgFlag');
    		if(checked==true){
    			sendMsgFlag.value = "true";
    		}else if(checked==false){
    			sendMsgFlag.value = "false";
    		}
    	}
    }
	</script>
	</head>

	<body class="body2">
	<form id="addVisitForm" action="" method="post">
		<input id="addVisitForm_visitUserIds" type="hidden" name="visitUserIds" />
		<input id="addVisitForm_visitUserNames" type="hidden" name="visitUserNames" />
		<input id="addVisitForm_sourceFileId" type="hidden" name="sourceFileId" value="${edocFile.id }"/>
	</form>
	<form id="save_form" action="shoreFileAction!shoreFile.action" method="post">
	<div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>共享设置
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 6%; position: relative; float: left;">
					<table  align="center" border="0" cellspacing="0" style="margin: 0.5em auto;width: 98%;">
						<tr>
							<td width="10%" style="padding-left:4px;">
								<strong><font color="blue">共享文件：</font></strong>
							</td>
							<td colspan="4" id="sourceFileNameTd">
									<img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }
							</td>
						</tr>
						
						<tr>
                            <td width="10%" style="padding-left:4px;">
                            <strong><font color="blue">消息提醒：</font></strong>
							</td>
							<td colspan="4">
								<input id="sysMsgType" type="checkbox" onclick="setSendMsgType(1)" value="msg">系统消息
							</td>
						</tr>
						 
						<!-- 
						<tr>
							<td align="right" width="10%">
								通知用户：
							</td>
							<td width="20%">
								<input type="radio" name="scope" onclick="changeScope(0)"
									checked>
								所有人
							</td>
							<td width="20%">
								<input type="radio" name="scope" onclick="changeScope(0)">
								本部门
							</td>
							<td width="20%">
								<input type="radio" name="scope" onclick="changeScope(1)">
								其他
							</td>
							<td width="126">
								<input id="selectUserBt" onclick="selectUser()" type="button" value="选择人员" disabled />
							</td>
						</tr>
						 -->
					</table>
				</div>
				<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onClick="selectUser()"><img src="icon/add.png"/>&nbsp;添加</a>
					</li>
					<li>
						<a href="javascript:void(0);" onClick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
			</div>
				<div style="width: 100%; height: 72%;" class="list_div">
					<table id="userTable" cellspacing="0" class="list_table" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th width="20%">
								名称
							</th>
							<th>
								权限
							</th>
							<th width="10%">
								操作
							</th>
						</tr>
						<c:forEach var="user" items="${visitUsers}">
						<tr>
							<td width="20%">
							<input name="visitUserIds" value="${user.visitUserId }" type="hidden"/>
							<input type="hidden" name="visitUserInfoId_${user.visitUserId }" value="${user.id }"/>
							<input type="hidden" name="visitUserName_${user.visitUserId }" value="${user.visitUserName }" />
								${user.visitUserName }
							</td>
							<td>
								<c:choose>
								<c:when test="${user.perView == 1}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="view" checked>预览
								</c:when>
								
								<c:when test="${user.perView == 0}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="view">预览
								</c:when>
								</c:choose>
								
								<c:choose>
								<c:when test="${user.perEdit == 1}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="edit" checked>编辑
								</c:when>
								
								<c:when test="${user.perEdit == 0}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="edit">编辑
								</c:when>
								</c:choose>
								
								<c:choose>
								<c:when test="${user.perDownLoad == 1}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="downLoad" checked>下载
								</c:when>
								
								<c:when test="${user.perDownLoad == 0}">
								<input type="checkbox" name="permission_${user.visitUserId }" value="downLoad">下载
								</c:when>
								</c:choose>
							</td>
							<td width="10%">
								<a href="javascript:void(0);" onClick="deleteRow(this,'${user.id }')">删除</a>
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>

				<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="1" cellpadding="2">
						<tr>
							<td align="center" height="24">
							<input type="button" class="button1" value="确认" onClick="onSubmit()">
							<input type="button" class="button1" value="取消" onClick="closeWin()">
							</td>
						</tr>
					</table>
				</div>
		</div>
		<input id="sourceFileId" name="sourceFileId" type="hidden" value="${edocFile.id }"/>
		<input id="sendMsgFlag" name="sendMsgFlag" type="hidden" value="false" />
		<input id="shoreFileId" name="shoreFileId" type="hidden" value="${shoreFile.id }">
		
		<!-- 
		<input id="shoreUserId" name="shoreFile.shoreUserId" type="hidden" value="${DOCUSER.id }"/>
		<input id="shoreUserName" name="shoreFile.shoreUserName" type="hidden" value="${DOCUSER.trueName }"/>
		 -->
		<!-- 
		<input id="parentId" name="parentId" type="hidden" value="${edocFile.parentId }" />
		<input id="shoredFlag" name="shoredFlag" type="hidden" value="${edocFile.isShored }" />
		<input id="shoreMulu" name="shoreMulu" type="hidden" />
		<input id="shoreFileId" name="shoreFileId" type="hidden" value="${shoreFile.id }">
		<input id="sendMsgFlag" name="sendMsgFlag" type="hidden" value="false" />
	 	-->
		</form>
	</body>
</html>

