<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <style type="text/css">
    	.footer1{
    		  text-align: center;
		    color: #fff;
		    background: #123670;
		    border-top: 1px solid #a7bfe3;
		    font-size: 11px;
		    height: 22px;
		    line-height: 22px;
    	}
    </style>
 </head>
<body>
	<div class="footer1">
		企业文档管理系统v1.0  &copy; 2011
	</div>
</body>
</html>