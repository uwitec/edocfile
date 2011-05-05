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
    <title>创建菜单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/security/permission.js"></script>
	<script type="text/javascript" src="js/Validator.js"></script>
	<style type="text/css">
		.main{
			position:relative;
			width:99%;
			height:auto;
			margin: 0 auto;
			border: 1px solid #a6c5e4;
		}
		.title{
			width:100%;
			height:25px;
			position:relative;
			border-bottom: 1px solid #a6c5e4;
			float:left;
			background:#e4f0f9;
			
			text-align:left;
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
			font-weight:bold;
		}
		.contents{
			width:100%;
			position:relative;
			float:left;
			height:246px;
			line-height:25px !important;
		}
		.t_bar{
			width:100%;
			height:25px;
			position:relative;
			float:left;
			border-bottom: 1px solid #a6c5e4;
		}
		.b_bar{
			width:100%;
			height:25px;
			position:relative;
			float:left;
			border-top: 1px solid #a6c5e4;
			align:center;
			background:#e4f0f9;
			text-align:center;
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
			font-weight:bold;
		}
		.form_table{}
		.form_table tr{}
		.form_table tr td{
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
		}
		
	</style>
	<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSubmit(){
			var form=document.getElementById('save_permission_action');
			if(form.fireEvent('onsubmit')){
				form.submit();
			}
			window.close();		//关闭窗口
		}
	</script>
  </head>
  
  <body>
    <div class="main">
    	<div class="title">
    		添加菜单信息
    	</div>
    	<form id="save_permission_action" action="menuAction!saveMenu.action" onsubmit="return Validator.Validate(this,3)" method="post">
    	<div class="contents">
    	<table width="80%" align="center" class="form_table">
    		<tr>
    			<td align="right" width="30%"><font color="red">*</font> 菜单名称：</td>
    			<td width="70%"><input type="text" name="menu.name" dataType="Require" msg="请输入菜单名称！"/></td>
    		</tr>
    		<tr>
    			<td align="right"><font color="red">*</font> 菜单链接：</td>
    			<td><input type="text" name="menu.url" dataType="Require" msg="请输入菜单链接！"/></td>
    		</tr>
    		<tr>
    			<td align="right"><font color="red">*</font>菜单标识：</td>
    			<td><input type="text" name="menu.enName" dataType="Require" msg="请输入菜单标识！"/></td>
    		</tr>
    		
    		<tr>
    			<td align="right"><font color="red">*</font>上级菜单：</td>
    			<td><input id="parentMenuText" type="text" value="" disabled dataType="Require" msg="请输入上级菜单！"/>&nbsp;&nbsp;<img src="icon/find.png" onclick="select_permission()"/></td>
    		</tr>
    		<tr>
    			<td>&nbsp;</td>
    			<td>&nbsp;</td>
    		</tr>
    		<tr>
    			<td  colspan="2" align="center"><font color="red">( * 带星号的为必填项 )</font></td>
    		</tr>
    	</table>
    	</div>
    	<input id="parentMenuId" type="hidden" name="menu.parentMenuId" />
    	</form>
    	<div class="b_bar">
    		<input type="button" class="button1" onclick="doSubmit()" value="保存"/>
    		<input type="button" class="button1" onclick="doClose()" value="关闭"/>
    	</div>
    </div>
  </body>
</html>
