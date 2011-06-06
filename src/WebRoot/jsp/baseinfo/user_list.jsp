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
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/baseinfo/user.js"></script>

		<!-- BEGIN Plugin Code -->
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.area_inner{
				margin:0 auto;
				width:100%;
				height:100%;
			}
			.font_red{
			    color:#B9090C;
			}
		</style>
		<script type="text/javascript">
		
			//重设密码
			function resetPassword(id){
				var form = document.getElementById("form1");
				form.action = "userAction!resetPassword.action?userId="+id+"&Rnd="+Math.random();
				form.submit();
			}
		</script>
	</head>
	<body>
		<form id="form1" action="" method="post">
		
		</form>
		<form id="queryForm" action="userAction!getUsersByOrgId.action" method="post">
			<input id="orgId" name="orgId" type="hidden" value="${orgId }"/>
			<input id="userName" name="userName" type="hidden" value="${userName }"/>
			<input type="hidden" name="currentPage" id="currentPage_param" value="${userPageVO.currentPage }" />
		</form>
		<div class="area_inner">
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								姓名
							</th>
							<th>
								登录名
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="user" items="${userPageVO.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${user.id }" type="checkbox"></input>
								</td>
								<td>
									${user.trueName }
								</td>
								<td>
									${user.loginName }
								</td>
								<td>
									<a href="javascript:void(0);" onclick="updateUser('${user.id }')" >修改</a>
									&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="deleteOne('${user.id }')">删除</a>
									&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="resetPassword('${user.id }')">重设密码</a>
								</td>
							</tr>
						</c:forEach>
				</table>
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
			<div class="bbar">
			</div>
		</div>
	</body>
</html>