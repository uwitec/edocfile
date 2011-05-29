<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户列表</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.inner_position{
				position:relative;
				width:100%;
				float:left;
				height:27px;
				border-bottom: 1px solid #a6c5e4;
				line-height:26px !important;
				text-indent:10px; 
				FONT-SIZE: 12px;
				list-style-type: none; 
				background:#e4f0f9;
				color:#416aa3;
			}
		</style>
		<script type="text/javascript">
			
			function searchUser(){
				var name = document.getElementById('userNameText').value;
				parent.frames("userlist_users").document.getElementById('userName').value=name;
				parent.frames("userlist_users").document.getElementById('queryForm').submit();
			}
			
			function deleteMore(){
				var array = parent.frames("userlist_users").document.getElementsByName("checkItem");
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
					var form = parent.frames("userlist_users").document.getElementById("queryForm");
					form.action = "userAction!deleteUser.action?invalid=1"+param+"&Rnd="+Math.random();
					form.submit();
				}else{
					alert('请选择要删除的记录!');
				}
			}
		</script>
	</head>
	
	<body class="body1">
		<div class="area">
			<input id="orgId" type="hidden" />
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>基础数据 &gt;&gt; 员工信息管理
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
					<ul id="nav">
						<li>
							<a href="javascript:void(0);" onclick="showAddUserWin()"><img src="icon/add.png"/>&nbsp;新建</a>
						</li>
						<!-- 
						<li>
							<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
						</li>
						 -->
						<li>
							<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
						</li>
						<li style="width:230px;">
							<input id="userNameText" value="" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchUser()" type="button" style="line-height:18px;" value="查询" class="button1" />
						</li>
					</ul>
			</div>
		</div>
	</body>
</html>
