<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript">
			
			//选择用户操作
			function selectAction(){
			
				var returnValue = [];
				var array = parent.frames("userlist_users").document.getElementsByName("checkItem");
				var length = array.length;
				var index = 0;
				for (var i = 0; i < length; i++) {
					if (array[i].checked == true) {
						var selectedRec = [];
						var selectedUserId = array[i].value;
						var selectedUserName = parent.frames("userlist_users").document.getElementById("username"+selectedUserId).value;
						selectedRec[0] = selectedUserId;
						selectedRec[1] = selectedUserName;
						returnValue[index] = selectedRec;
						index++;
					}
				}
				
				parent.window.returnValue=returnValue; 
				parent.window.close();
			}
			function closeWin(){
				parent.window.close();
			}
		</script>
	</head>
	
	<body class="body1">
	<div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						<input type="button" onclick="selectAction()" value="保&nbsp;&nbsp;存"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="closeWin()" value="关&nbsp;&nbsp;闭"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</body>
</html>
