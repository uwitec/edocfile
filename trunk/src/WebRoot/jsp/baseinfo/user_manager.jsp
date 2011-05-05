<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户列表</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		
	</head>
	<frameset rows="60,*" framespacing="0">
		<frame src="jsp/baseinfo/userlistpage_top.jsp" name="usermanagertbar" marginwidth=0 marginheight=0 scrolling="no" frameborder=0 noresize>
		<frameset cols="250,5,*">
			<frame src="jsp/baseinfo/org_tree.jsp" name="userlist_orgtree" marginwidth=0 marginheight=0 scrolling="no" frameborder=0 noresize>
			<frame src="jsp/baseinfo/control.jsp" name="userlist_control" marginwidth=0 marginheight=0 scrolling="no" frameborder=0>
			<frame src="userAction!getUsersByOrgId.action" name="userlist_users" marginwidth=0 marginheight=0  frameborder=0>
		</frameset>
	</frameset>
</html>
