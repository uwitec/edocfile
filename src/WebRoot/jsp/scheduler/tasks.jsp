<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>任务列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<link href="css/tbar.css" rel="stylesheet" type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
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
		function addTask(){
			showModalDialog("jsp/scheduler/addTask.jsp?&Rnd="+Math.random(), "", "dialogWidth:800px; dialogHeight:600px;help:no;scroll:no;status:no");
			doSearch();
		}
		
		function updateTask(id){
			showModalDialog("schedulerAction!breforeUpdateTask.action?id="+id+"&Rnd="+Math.random(), "", "dialogWidth:800px; dialogHeight:600px;help:no;scroll:no;status:no");
			doSearch();
		}
		
		function startTask(id){
			var form = document.getElementById('form1');
			form.action  = "schedulerAction!startTask.action?id="+id+"&Rnd="+Math.random();
			form.submit();
		}
		
		function stopTask(id){
			var form = document.getElementById('form1');
			form.action  = "schedulerAction!stopTask.action?id="+id+"&Rnd="+Math.random();
			form.submit();
		}
		function doSearch(){
			var form = document.getElementById('form1');
			form.action  = "schedulerAction!getTasks.action";
			form.submit();
		}
	</script>
  </head>
  
  <body class="body1">
  	<form id="form1" action="" method="POST">
  		
  	</form>
    <div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>
							系统管理&gt;&gt;任务调度管理
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="addTask()"><img src="icon/add.png"/>&nbsp;新增</a>
					</li>
					<!-- 
					<li>
						<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
					 -->
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								任务名称
							</th>
							<th>
								运行状态
							</th>
							<th>
								调度类型
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="task" items="${tasks}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${task.id }" type="checkbox"></input>
								</td>
								<td id="fileName${task.id }" align="left">
									${task.displayName }
								</td>
								<td>
									<c:if test="${task.state==1}">
										<font color="green">运行中</font>
									</c:if>
									<c:if test="${task.state==0}">
										<font color="red">已停止</font>
									</c:if>
								</td>
								<td>
									
								</td>
								<td align="center">
									<a href="javascript:void(0);" onclick="updateTask('${task.id }');">修改</a>
									&nbsp;&nbsp;
									<c:if test="${task.state==1}">
										<a href="javascript:void(0);" onclick="stopTask('${task.id }');">停止任务</a>
									</c:if>
									<c:if test="${task.state==0}">
										<a href="javascript:void(0);" onclick="startTask('${task.id }');">启动任务</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
				</table>
			</div>
			<div class="bbar">
			</div>
		</div>
  </body>
</html>
