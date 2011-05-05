<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
	String fileName = (String)request.getAttribute("fileName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看图片</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body>
  	<div style="margin:0px auto;" >
  	<img src="temp/<%=fileName %>">
  	</div>
  </body>
</html>
