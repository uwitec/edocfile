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
			
			function searchUser(){
				var name = document.getElementById('userNameText').value;
				parent.frames("userlist_users").document.getElementById('userName').value=name;
				parent.frames("userlist_users").document.getElementById('queryForm').submit();
			}
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
	
	<body class="body2">
	<input id="orgId" type="hidden" />
	<div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>系统管理 &gt;&gt; 模板管理 &gt;&gt; 模板字段设计
					</td>
				</tr>
			</table>
		</div>
		<div class="tbar" style="position: relative; float: left;width: 100%; height:24px;">
			<table width="100%" border="0" cellspacing="0">
              <tr>
                <td align="right">
                	<input id="userNameText" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchUser()" type="button" style="line-height:18px;" value="查询"/>
                </td>
              </tr>
            </table>

		</div>
	</div>
		<!-- 
		<div class="area">
			<div class="win_toolbar">
	    		<input type="button" onclick="selectAction()" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="closeWin()" value="关闭"/>
	    	</div>
			<div class="tbar" style="border-top: 1px solid #a6c5e4;">
					<ul id="nav" style="float:right;">
						<li style="width:230px;">
							<input id="userNameText" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchUser()" type="button" style="line-height:18px;" value="查询" class="button1" />
						</li>
					</ul>
			</div>
		</div>
		 -->
	</body>
</html>
