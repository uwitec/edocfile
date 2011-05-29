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
		<title>全文检索</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<!-- BEGIN Plugin Code -->
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.font_red{
			    color:#B9090C;
			}
			.rs_div{
				position:relative;
				width:70%;
				left:10px;
				float:left;
				margin-top:10px;
				min-height:100px;
				height:auto;
			}
			.content1{
				position:relative;
				float:left;
				width:100%;
				height:540px;
				color:#15428b;
				overflow-y:scroll;
				scrollbar-face-color: #CCCCCC;
				scrollbar-highlight-color: #ffffff;
				scrollbar-shadow-color: #ffffff;
				scrollbar-3dlight-color: #cccccc;
				scrollbar-arrow-color: #888888;
				scrollbar-track-color: #f5f5f5;
				scrollbar-color: #B0B0B0;
				scrollbar-darkshadow-color: #ccccc0;
			}
			.foot{
				position:relative;
				width:100%;
				float:left;
				height:25px;
				border-top:#7b9ebd 1px solid;
			}
			.div1{
				position:relative;
				float:left;
				width:100%;
				height:120px;
				border-bottom:#7b9ebd 1px solid;
			}
			.div1:hover {
				background-color: #CCCCCC;
				color: #CCCCCC;
				filter:progid:DXImageTransform.Microsoft.gradient(startcolorstr=#FFFFFF,endcolorstr=#7b9ebd,gradientType=0);
			}
			.from_table2{
				margin: 0.5em 0.5em ;width: 90%;
			}
			.from_table2 th{
				font-size: 12px;
				color: #333333;
				line-height:23px;
				text-align:center;
			}
			.from_table2 td{
				height:23px;
				padding-top:4px;
				padding-left:4px;
				padding-right:4px;
			}
		</style>
		<script type="text/javascript">
			//翻页操作
			function openPage(currentPage){
				document.getElementById("currentPage_param").value=currentPage;
				doSearch();
			}
			
			//查询
			function doSearch(){
				var form = document.getElementById("queryForm1");
				form.action = "searchAction!documentSearch.action";
				form.submit();
			}
		</script>
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${docs.currentPage }" />
			<input type="hidden" name="keyWord" value="${keyWord }">
		</form>
		<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>全文检索
						</td>
					</tr>
				</table>
			</div>
			<div class="content1">
			<c:forEach var="r" items="${docs.result}">
			<div class="div1">
				<table cellspacing="0" class="from_table2">
					<tr>
						<td colspan="2" align="left">
							<strong><font color="blue">文件名称：${r.sourceFileName }</font></strong>			
						</td>
				  </tr>
					<tr>
						<td width="5" align="right">&nbsp;</td>
						<td width="98%" rowspan="2">
						<span style="height:50px;overflow:hidden;text-overflow :ellipsis;word-wrap: break-word">
						${r.contents }
						</span>
						</td>
				  </tr>
					<tr>
						<td align="right">&nbsp;</td>
				  </tr>
					<tr>
						<td colspan="2" align="left">
						创建人：<font color="blue">${r.creatorName }</font>
						&nbsp;&nbsp;&nbsp;&nbsp;创建时间:<font color="blue">${r.createTime }</font>
						&nbsp;&nbsp;&nbsp;&nbsp;大小:<font color="blue">${r.fileSize }</font>
						&nbsp;&nbsp;&nbsp;&nbsp;当前版本号:<font color="blue">${r.versionNum }</font>
					</td>
				  </tr>
				</table>
			</div>
			</c:forEach>
			</div>
			<div class="foot">
			<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="center" nowrap>共 
                  		<span class="font_red">${docs.totalRows }</span> 条数据 当前第 <span class="font_red">${docs.currentPage }</span> 
                  		页 共 <span class="font_red">${docs.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${docs.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${docs.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${docs.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
		</div>
	</body>
</html>