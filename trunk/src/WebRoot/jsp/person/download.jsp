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
		<title>文件下载</title>
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
		
	</head>

	<body class="body1">
	<form id="form1" action="" method="post">
	<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>个人维护 &gt;&gt; 文件下载
						</td>
					</tr>
				</table>
			</div>
			
			<div class="contents" id="tabContents">
		    	<div>
			    	<table cellspacing="0" class="from_table" style="overflow-x:scroll;">
			    		 <tr >
						    <td width="20%" colspan="2" align="left"><strong>文件下载</strong></td>
						  </tr>
						  <tr>
						    <td width="80%" align="left">在线预览、编辑文档工具(DocView.exe)</td>
						    <td width="20%" align="center"><a href="sfile/DocView.exe">下载</a> </td>
						  </tr>
						  <tr>
						    <td align="left">----</td>
						    <td align="center"><a>下载</a></td>
						  </tr>
						</table>

		      </div> 
		    </div>
	</div>
	</form>
</body>
</html>

