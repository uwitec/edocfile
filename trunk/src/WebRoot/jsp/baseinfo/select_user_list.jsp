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
		<title>用户信息</title>
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
	</head>
	<body>
		<form id="queryForm" action="userAction!getUsersByOrgId.action" method="post">
			<input id="orgId" name="orgId" type="hidden" value="${orgId }"/>
			<input id="userName" name="userName" type="hidden" value="${userName }"/>
			<input type="hidden" name="currentPage" id="currentPage_param" value="${userPageVO.currentPage }" />
			<input type="hidden" name="forward" id="forward" value="${forward }" />
		</form>
		<div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
			<div style="width: 100%; height: 75%;" class="list_div2">
					<table id="userTable" cellspacing="0" class="list_table" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								姓名
							</th>
							<th>
								登录名
							</th>
						</tr>
						<c:forEach var="user" items="${userPageVO.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${user.id }" type="checkbox"></input>
									<input id="username${user.id }" value=${user.trueName } type="hidden"></input>
								</td>
								<td>
									${user.trueName }
								</td>
								<td>
									${user.loginName }
								</td>
							</tr>
						</c:forEach>
					</table>
			</div>
						
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${userPageVO.totalRows }</span> 条数据 当前第 <span class="font_red">${userPageVO.currentPage }</span> 
                  		页 共 <span class="font_red">${userPageVO.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${userPageVO.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${userPageVO.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${userPageVO.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
        </div>
	</body>
</html>