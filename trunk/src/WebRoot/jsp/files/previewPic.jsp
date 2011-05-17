<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
	String fileName = (String)request.getAttribute("fileName");
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看图片</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<style type="text/css">
  			.area{
				margin:0 auto;
				width:100%;
				height:100%;
				text-align:center;
			}
  	
  	</style>
  </head>
  
  <body>
  	<div class="area">
  	<img src="temp/<%=fileName %>">
  	</div>
  </body>
</html>
