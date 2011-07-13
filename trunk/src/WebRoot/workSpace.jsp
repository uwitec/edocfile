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
		<title>工作台</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			
			.index_panel{
				position: relative;
				width: 500px;
				height: 267px;
				float: left;
				margin-left: 2%; 
				margin-right: 2%; 
				margin-bottom: 1%;
				margin-top: 1%;
				border: 1px solid #CCCCCC;
			}
			.panel_title{
				position: relative;
				width: 100%;
				height: 27px;
				float: left;
				background: url(images/workspace_title_bg.gif) repeat-x;
			}
			.title_left{
				position: relative;
				width: 250px;
				height: 27px;
				float: left;
				line-height:25px;
				text-indent: 1em;
				font-weight: bold;
			}
			.title_right{
				position: relative;
				width: 250px;
				height: 27px;
				float: left;
				line-height:25px;
				text-align:right;
				font-weight: bold;
			}
			.panel_content{
				position: relative;
				width: 500px;
				height:240px;
				float: left;
				overflow: auto; 
			}
			a:link{text-decoration:none;color:#1060A4;} 　　
			a:hover{text-decoration:none;color:#1060A4;} 　　
			a:visited{text-decoration:none;color:#1060A4;} 
		</style>
		<script type="text/javascript">
		</script>
</head>
	<body class="body1">
	<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>我的工作台
						</td>
					</tr>
				</table>
			</div>
			<div class="content">
					<div class="index_panel">
						<div class="panel_title">
							<div class="title_left">
							最近打开
							</div>
							<div class="title_right">
							
							</div>
						</div>
						<div class="panel_content">
							<iframe id="newsFrame" src="fileUseRecordAction!workSpaceCurrentFileUseRecords.action" scrolling="no" style="width: 98%; height: 98%;" frameborder="0"></iframe>
						</div>
					</div>
					
					<div class="index_panel">
						<div class="panel_title">
							<div class="title_left">
							短消息
							</div>
							<div class="title_right">
							<a href="messageAction!getReceiveMessages.action"  target="perspective_content">更多>></a>&nbsp;&nbsp;
							</div>
						</div>
						<div class="panel_content">
							<iframe id="newsFrame" src="messageAction!getReceiveMessages.action?pageSize=10&forward=workSpaceMsg&readSate=0" scrolling="no" style="width: 98%; height: 98%;" frameborder="0"></iframe>
						</div>
					</div>
  			</div>
		</div>
	</body>
</html>