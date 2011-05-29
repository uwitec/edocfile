<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<title>员工基本信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.contents{
				margin: 0 auto;
				width:100%;
				position:relative;
				float:left;
				height:246px;
				line-height:25px !important;
			}
		</style>
		
		<script type="text/javascript">
			function doSubmit(){
				var oldPassword = document.getElementById("oldPassword").value;
				if(!oldPassword){
					alert("请输入原密码,请确认后再试!");
					return;
				}
				
				var newPassword = document.getElementById("newPassword").value;
				if(!newPassword){
					alert("请输入新密码,请确认后再试!");
					return;
				}
				
				var newPassword2 = document.getElementById("newPassword2").value;
				if(!newPassword2){
					alert("请输入确认密码,请确认后再试!");
					return;
				}
				
				var oldPassword2 = document.getElementById("oldPassword2").value;
				if(oldPassword2!=oldPassword){
					alert("原密码不正确,请确认后再试!");
					return;
				}
				
				if(newPassword2!=newPassword){
					alert("新密码与确认密码不一致,请确认后再试!");
					return;
				}				
				var form = document.getElementById("form1");
				form.action = "userAction!updatePassword.action";
				form.submit();
			}
			
		</script>
	</head>

	<body class="body1">
	<input id="oldPassword2" type="hidden" value="${DOCUSER.password }">
	<form id="form1" action="" method="post">
	<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>个人维护 &gt;&gt; 修改密码
						</td>
					</tr>
				</table>
			</div>
			
			<div class="contents" id="tabContents">
		    	<div>
			    	<table cellspacing="0" class="from_table" style="overflow-x:scroll;">
			    		 <tr >
						    <td width="20%" colspan="2" align="left"><strong>修改密码</strong></td>
						  </tr>
						  <tr>
						    <td width="20%" align="right">原密码：</td>
						    <td width="80%"><input id="oldPassword" type="password" name="password" /> </td>
						  </tr>
						  <tr>
						    <td align="right">新密码：</td>
						    <td><input id="newPassword" name="newPassword" type="password" /></td>
						  </tr>
						  <tr>
						    <td align="right">确认密码：</td>
						    <td><input id="newPassword2" type="password" /></td>
						  </tr>
						  <tr>
						    <td colspan="2" align="center"><input type="button" onclick="doSubmit();" value="提交" /> &nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="重填" /></td>
						  </tr>
						</table>

		      </div> 
		    </div>
	</div>
	</form>
</body>
</html>

