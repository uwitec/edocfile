<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
	</head>
	<input type="hidden" id="test" value="checn" />
	<frameset rows="61,*,30" framespacing="0">
		<frame src="jsp/baseinfo/select_user_title.jsp" name="usermanagertbar" marginwidth=0 marginheight=0 scrolling="no" frameborder=0 noresize>
		<frameset cols="200,5,*">
			<frame src="jsp/baseinfo/org_tree.jsp" name="userlist_orgtree" marginwidth=0 marginheight=0 scrolling="no" frameborder=0 noresize>
			<frame src="jsp/baseinfo/control.jsp" name="userlist_control" marginwidth=0 marginheight=0 scrolling="no" frameborder=0>
			<frame src="userAction!getUsersByOrgId.action?forward=toSelectUsers" name="userlist_users" marginwidth=0 marginheight=0  frameborder=0>
		</frameset>
		<frame src="jsp/baseinfo/select_user_bbar.jsp" name="usermanagerbbar" marginwidth=0 marginheight=0 scrolling="no" frameborder=0 noresize>
	</frameset>
</html>
