<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<base target="_self">
		<title>添加文件</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript" src="js/Validator.js"></script>
		<script type="text/javascript">
    	function doClose(){
			this.close();
		}
		function doSubmit(){
			var form=document.getElementById('save_form');
			var c = document.getElementById('parentId').value;
			form.submit();
			window.close();		//关闭窗口
		}
		function init(){
			var params = window.dialogArguments;	//参数为上一级文件夹的Id
			var c = document.getElementById('parentId').value;
			if(!c){
				document.getElementById('parentId').value=params[0];
			}
		}
		</script>
	</head>

	<body class="body2" onload="init()">
	    <form id="save_form" action="fileAction!createFolder.action" onsubmit="return Validator.Validate(this,3)" method="post">
    	<input id="parentId" type="hidden" name="edocFile.parentId" />
	<div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>我的文件夹 &gt;&gt; 创建文件夹
					</td>
				</tr>
			</table>
		</div>
				<div style="width: 100%; height: 230px;" class="list_div3">
					<table width="100%" align="center">
			    		<tr>
			    			<td align="right" width="22%"><font color="red">*</font> 文件夹名称：</td>
		    			  <td width="78%"><input type="text" name="edocFile.fileName" dataType="Require" msg="请输入正确的姓名！"/></td>
		    		  </tr>
                      <tr>
			    			<td align="right" width="22%"><font color="red">*</font> 文件夹描述：</td>
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
								<input type="button" class="button" value="确&nbsp;&nbsp;认" onClick="doSubmit()">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="button" value="取&nbsp;&nbsp;消" onClick="doClose()">
							</td>
						</tr>
					</table>

				</div>
		</div>
		</form>
	</body>
</html>

