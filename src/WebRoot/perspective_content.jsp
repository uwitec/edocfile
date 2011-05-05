
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>我的文件夹</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/files/files.js"></script>
		<!-- BEGIN Plugin Code -->
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
			function selectAllCheckbox(){
				var flag = document.getElementById("selectAll");
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				if (flag.checked == true) {
					for (var i = 0; i < length; i++) {
						array[i].checked = true;
					}
				} else {
					for (var i = 0; i < length; i++) {
						array[i].checked = false;
					}
				}
			}
			function openPage(currentPage){
				document.getElementById("currentPage_param").value=currentPage;
				doSearch();
			}
			function doSearch(){
				var form = document.getElementById("queryForm1");
				form.action = "fileAction!getRootFileFromMyFolder.action";
				form.submit();
			}
		</script>
	</head>
	<body>
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${filePageVO.currentPage }" />
		</form>
		<div class="area">
		sdf
		</div>
	</body>
</html>