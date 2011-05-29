<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<base target="_self">
		<title>创建用户</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript" src="js/Validator.js"></script>
		<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSubmit(){
			var form=document.getElementById('save_form');
			var trueName = document.getElementById("trueName").value;
			if(!trueName){
				alert("请输入姓名!");
			}
			var loginName = document.getElementById("loginName").value;
			if(!loginName){
				alert("请输入登录名!");
			}
			var password = document.getElementById("password").value;
			if(!trueName){
				alert("请输入密码!");
			}
			var orgName = document.getElementById("orgName").value;
			if(!trueName){
				alert("请选择科室!");
			}
			
			var userId = document.getElementById("userId").value;
			if(userId){
				form.action = "userAction!updateUser.action?Rnd="+Math.random();
			}else{
				form.action = "userAction!saveUser.action?Rnd="+Math.random();
			}
			form.submit();
			window.close();
		}
	</script>
	</head>

	<body class="body2">
		<form id="save_form" action="" method="post">
		<input id="userId" type="hidden" name="user.id" value="${user.id }">
			<div
				style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
				<div
					style="width: 100%; height: 35px; position: relative; float: left; top: 0px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td style="font-size: 13px;" align="center" width="30" nowrap>
								<img src="images/title.gif" width="7" height="33">
							</td>
							<td>
								<strong>您当前的位置：</strong>新建员工信息
							</td>
						</tr>
					</table>
				</div>
				<div style="width: 100%; height: 230px;" class="list_div3">
					<table width="80%" align="center" class="form_table">
						<tr>
							<td align="right" width="30%">
								<font color="red">*</font> 姓&nbsp;&nbsp;名：
							</td>
							<td width="70%">
								<input id="trueName" type="text" name="user.trueName" value="${user.trueName }" />
							</td>
						</tr>
						<tr>
							<td align="right" width="30%">
								<font color="red">*</font> 登录名：
							</td>
							<td width="70%">
								<input id="loginName" type="text" name="user.loginName" value="${user.loginName }" />
							</td>
						</tr>
						<tr>
							<td align="right" width="30%">
								<font color="red">*</font> 密&nbsp;&nbsp;码：
							</td>
							<td width="70%">
								<input id="password" type="password" name="user.password" value="${user.password }" />
							</td>
						</tr>
						<tr>
							<td align="right" width="30%">
								<font color="red">*</font> 科&nbsp;&nbsp;室：
							</td>
							<td width="70%">
								<input id="orgName" type="text" value="${dept.deptName }" disabled />
								&nbsp;&nbsp;
								<img src="icon/find.png" onclick="show_select_org_win()" />
								<input id="orgId" type="hidden" name="user.orgId" value="${dept.id }" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<font color="red">( * 带星号的为必填项 )</font>
							</td>
						</tr>
					</table>
				</div>
				<div style="width: 100%; height: 5%; position: relative; float: left;" class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<input type="button" class="button1" onclick="doSubmit()" value="保存" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="button1" onclick="doClose()" value="关闭" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
