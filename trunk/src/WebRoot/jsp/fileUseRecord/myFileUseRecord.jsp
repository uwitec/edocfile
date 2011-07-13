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
		<title>文件操作日志</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript">
		
			//翻页操作
			function openPage(currentPage){
				document.getElementById("currentPage_param").value=currentPage;
				doSearch();
			}
			
			
			//查询
			function doSearch(){
				var form = document.getElementById("queryForm1");
				form.action = "fileUseRecordAction!getMyFileUseRecords.action?Rnd="+Math.random();
				form.submit();
			}
		</script>
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
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${pageValues.currentPage }" />
			<input type="hidden" name="useType" id="useType" value="${useType }" />
		<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>
							操作日志
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li style="width:230px;">
							<input id="fileName" name="fileName" value="${fileName }" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchFile()" type="button" style="line-height:18px;" value="查询" class="button1" />
					</li>
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th>
								文件名称
							</th>
							<th>
								操作类型
							</th>
							<th>
								操作日期
							</th>
						</tr>
						<c:forEach var="v" items="${pageValues.result}">
							<tr>
								<td align="left">
									${v.sourceFileName }
								</td>
								<td>
									<c:if test="${v.useType==0}">
									预览
									</c:if>
									<c:if test="${v.useType==1}">
									编辑
									</c:if>
									<c:if test="${v.useType==2}">
									下载
									</c:if>
									<c:if test="${v.useType==3}">
									借阅
									</c:if>
									<c:if test="${v.useType==4}">
									复制
									</c:if>
									<c:if test="${v.useType==5}">
									剪切
									</c:if>
								</td>
								<td>
									${v.createTime }
								</td>
							</tr>
						</c:forEach>
				</table>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${pageValues.totalRows }</span> 条数据 当前第 <span class="font_red">${pageValues.currentPage }</span> 
                  		页 共 <span class="font_red">${pageValues.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${pageValues.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${pageValues.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${pageValues.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			<div class="bbar">
			</div>
		</div>
		</form>
	</body>
</html>