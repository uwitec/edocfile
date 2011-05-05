<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	
		.save { background-image:url(icon/add.png) !important;}	
	</style>
	<script type="text/javascript">
		function openA(){
			window.open("test5.jsp","","resizable=yes,status=no,toolbar=no,menubar=no,location=no");
			window.opener = null;
			window.close();
		}
	</script>
  </head>
  
  <body>
  	<a href="javascript:openA();">测试</a>
    <a href="menu!test.action">测试</a>
    <a href="menu!createMenu.action">创建菜单测试</a>
    
    <input type="button" icon="save" value="保存">
  </body>
</html>
