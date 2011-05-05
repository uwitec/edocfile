<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <base href="<%=basePath%>">
    <base target="_self">
    <title>添加角色</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/Validator.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSubmit(){
			var oldRoleName =document.getElementById("oldRoleName").value;
			var roleName = document.getElementById("roleName").value;
			if(oldRoleName==roleName){		//说明角色名称没有改变,不需要验证
				var form=document.getElementById('save_form');
				form.action = "roleAction!updateRole.action";
				form.submit();
				window.close();
			}else{
				var url = "roleAction!isExist.action?roleName="+roleName;
				$.ajax({
			     		url:url, 
			     		type:'post',
			    		error: function(){
			    			alert('操作出错,请稍候重试!');
			     		},
			    		success: function(json){
			    			if(json){
			    				var jsonValue = eval('(' + json + ')');
			    				var isExist = jsonValue.isExist;
			    				if(isExist=="true"){
			    					alert("存在同名的角色信息!");
			    				}else{
			    					var form=document.getElementById('save_form');
									form.action = "roleAction!updateRole.action";
									form.submit();
									window.close();
			    				}
			    			}
			      		}
			     });		
			}
		}
	</script>
  </head>
  
  <body class="body1">
   <form id="save_form" action="" method="POST">
   <input name="role.id" type="hidden" value="${role.id }" />
   <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>角色管理 &gt;&gt; 添加角色
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 230px;" class="list_div3">
	    	<table width="80%" >
	    		<tr>
	    			<td align="right" width="30%"><font color="red">*</font> 角色名称：</td>
	    			<td width="70%">
	    			<input id="roleName"  type="text" name="role.roleName" value="${role.roleName }"/>
	    			<input id="oldRoleName"  type="hidden" value="${role.roleName }"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td align="right" width="30%">选择文件：</td>
	    			<td width="70%">
	    				<textarea name="role.desc" rows="5" cols="40">${role.desc }</textarea>
	    			</td>
	    		</tr>
	    	</table>
    	</div>
    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<input type="button" class="button" value="确&nbsp;&nbsp;认" onclick="doSubmit()"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="button" value="取&nbsp;&nbsp;消" onClick="doClose()" />
							</td>
						</tr>
					</table>

		</div>
		</div>
    </form>
  </body>
</html>
