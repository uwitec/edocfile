<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页显示最近打开的文件记录</title>
    <script type="text/javascript" src="js/files/files.js"></script>
	<style type="text/css">
		#xuxian {border-top:1px dashed #cccccc;height: 1px;overflow:hidden;}
		td{
			color:#1060A4;
			font-size:12px;
			font-weight:normal;
			line-height:23px;
		}
		a:link{text-decoration:none;color:#1060A4;} 　　
		a:hover{text-decoration:none;color:#1060A4;} 　　
		a:visited{text-decoration:none;color:#1060A4;} 
	</style>
  </head>
  
  <body>
  <table width="100%" border="0" cellspacing="0">
  <c:forEach var="v" items="${fileUseRecords}">
  	 <tr>
      <td width="200"><a href="javascript:void(0);" onclick="showFileInfo('${v.sourceFileId }','${v.sourceFileName }')">${v.sourceFileName }</a></td>
      <td align="right">[${v.createTime }]</td>
    </tr>
    <tr>
      <td colspan="2">
      	<div id="xuxian"></div>
      </td>
    </tr>
  </c:forEach>
  </table>
</body>
</html>
