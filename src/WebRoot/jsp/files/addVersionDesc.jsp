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
    <title>添加版本更改描述</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript" src="js/Validator.js"></script>
	<script type="text/javascript">
		function doSubmit(){
			var textarea = document.getElementById('textarea').value;
			var params = [];
			params[0] = textarea;
			window.returnValue= params;
			window.close();
		}
		function init(){
			var params = window.dialogArguments;	//参数为上一级文件夹的Id
			document.getElementById('fileName').value=params[0];
		}
	</script>
  </head>
  
  <body class="body2" onload="init()">
   <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>版本更新说明
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 230px;" class="list_div3">
	    	<table width="100%" align="center">
	    		<tr>
	    			<td align="right" width="22%">文件名称：</td>
	    			<td width="78%"><input  type="text" id="fileName" readOnly size="45"/></td>
	    		</tr>
                <tr>
	    			<td align="right" width="22%">更改说明：</td>
	    			<td width="78%"><label>
	    			  <textarea name="textarea" id="textarea" cols="45" rows="5"></textarea>
	    			</label></td>
                </tr>
	    	</table>
   	 	</div>
    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<input type="submit" class="button" onclick="doSubmit()" value="确&nbsp;&nbsp;认" />
							</td>
						</tr>
					</table>

				</div>
		</div>
  </body>
</html>
