<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title>登 录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css">
	<script type="text/javascript">
		function do_login(){
			var username=document.getElementById('username').value;
			if(username==""){
				var error_msg=document.getElementById('error_msg');
				error_msg.innerHTML="<font color='red'>用户名不能为空!</font>";
				return;
			}
			
			var password=document.getElementById('password').value;
			if(password==""){
				var error_msg=document.getElementById('error_msg');
				error_msg.innerHTML="<font color='red'>密码不能为空!</font>";
				return;
			}
			var form = document.getElementById("loginForm");
			form.action = "userAction!doLogin.action";
			form.submit();
			//var error_msg=document.getElementById('error_msg');
			//error_msg.innerHTML="&nbsp;";
			//window.location="index.jsp"
		}
	</script>
  </head>
  
  <body>
    <div class="login_div">
		<form id="loginForm" action="">
		  <table width="100%" border="0" align="center" cellspacing="0" style="margin:auto;">
	      	  <tr>
	            <td colspan="3" align="center" id="error_msg">
	            	&nbsp;
	            </td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td width="10%">&nbsp;</td>
	            <td align="center">
	            	用户名：<input id="username" name="j_username" maxlength="20" style="width:160px"/>   
	            </td>
	            <td width="10%">&nbsp;</td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td align="center">
	            	&nbsp;&nbsp;&nbsp;&nbsp;密码：<input id="password" name="j_password" type="password" size="20" style="width:160px;"/>     
	            </td>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	            <td align="center">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<input name="Submit" type="button" onclick="do_login()" class="btn" id="Submit" value="登 录"/>
	            	<input name="Submit" type="reset" class="btn" id="Submit" value="取 消"/>            
	            </td>
	            <td>&nbsp;</td>
	          </tr>
	        </table>
	      </form>
		</div>
  </body>
</html>
