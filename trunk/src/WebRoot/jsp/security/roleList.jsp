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
		<title>角色管理</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
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
		<script type="text/javascript">
			function addRole(){
				showModalDialog("jsp/security/addRole.jsp", "", "dialogWidth:600px; dialogHeight:300px;help:no;scroll:no;status:no");
				doSearch();
			}
			
			function updateRole(id){
				showModalDialog("roleAction!getRoleById.action?id="+id, "", "dialogWidth:600px; dialogHeight:300px;help:no;scroll:no;status:no");
				doSearch();
			}
			
			function doSearch(){
					var form = document.getElementById("queryForm1");
					form.action = "roleAction!getRoles.action?&Rnd=" + Math.random();
					form.submit();
			}
			function selectAllCheckbox(){
				var flag = document.getElementById("selectAll");
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				if (flag.checked == true) {
					for (var i = 0; i < length; i++) {
						array[i].checked = true;
					}
				} else {
					for (var i = 0; i < length; i++) {
						array[i].checked = false;
					}
				}
			}
			
						//删除多条记录
			function deleteMore(){
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				var param = "";
				var selectFlag = false;
				for (var i = 0; i < length; i++) {
					if (array[i].checked == true) {
						selectFlag = true;
						param +="&deleteParams="+array[i].value;
					}
				}
				if(selectFlag){
					var form = document.getElementById("queryForm1");
					form.action = "roleAction!deleteRoles.action?"+param;
					form.submit();
				}else{
					alert('请选择要删除的记录!');
				}
			}
			
			function deleteOne(id){
				var form = document.getElementById("queryForm1");
				form.action = "roleAction!deleteRoles.action?deleteParams="+id;
				form.submit();
			}
			
			function setPermission(id){
				// showModalDialog("jsp/security/setPermissions.jsp", "", "dialogWidth:450px; dialogHeight:630px;help:no;scroll:no;status:no");
				 showModalDialog("roleAction!beforeSetPermissions.action?roleId="+id+"&Rnd=" + Math.random(), "", "dialogWidth:450px; dialogHeight:630px;help:no;scroll:no;status:no");
				
			}
			function showRoleUser(id){	//管理角色用户
				showModalDialog("userAction!getRoleUser.action?roleId="+id, "", "dialogWidth:1000px; dialogHeight:500px;help:no;scroll:no;status:no");
			}
		</script>
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${rolesPageVO.currentPage }" />
			<input type="hidden" name="pageSize" value="${rolesPageVO.pageSize }" />
			
		</form>
		<div class="area">
			<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>系统管理 &gt;&gt; 角色管理
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="addRole()"><img src="icon/add.png"/>&nbsp;新增</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								名称
							</th>
							<th>
								描述
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="v" items="${rolesPageVO.result}">
							<tr>
								<td width="5" align="center">
									<input name="checkItem" value="${v.id }" type="checkbox"></input>
								</td>
								<td align="left">
									${v.roleName }
								</td>
								<td>
									${v.desc }
								</td>
								<td width="200"  align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="setPermission('${v.id }')">设置权限</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showRoleUser('${v.id }')">角色用户</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="updateRole('${v.id }')">修改</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteOne('${v.id }')">删除</a>
								</td>
							</tr>
						</c:forEach>
				</table>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${rolesPageVO.totalRows }</span> 条数据 当前第 <span class="font_red">${rolesPageVO.currentPage }</span> 
                  		页 共 <span class="font_red">${rolesPageVO.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${rolesPageVO.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${rolesPageVO.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${rolesPageVO.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			<div class="bbar">
			</div>
		</div>
	</body>
</html>