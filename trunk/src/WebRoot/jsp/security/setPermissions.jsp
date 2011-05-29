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
    <title>设置功能菜单</title>
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="jsplugin/checkboxdtree/dtree.css">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="jsplugin/checkboxdtree/dtree.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
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
			min-height:546px;
			line-height:25px !important;
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
	</style>
	<script type="text/javascript">
		
		function doClose(){
			this.close();
		}
		
		function doSubmit(){
			var ids = "";
			var form = document.getElementById("testForm");
			for (var i=0; i<form.elements.length; i++) {
				var element = form.elements[i];
				if (element.name == "id" && element.type=='checkbox'){
					if( element.checked == true ){
						ids = ids + element.value + ",";
					}
				}
			}		
			var menuIds = document.getElementById("menuIds");
			menuIds.value = ids;
			
			var form = document.getElementById("form1");
			form.action = "roleAction!setPermissions.action?&Rnd=" + Math.random();
			form.submit();
		}
	</script>
  </head>
  
  <body class="body2">
  <form id="form1" action="" method="post">
  	<input type="hidden" name="roleId" value="${roleId }" />
  	<input type="hidden" id="menuIds" name="menuIds" />
  </form>
     <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
    	<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>系统管理 &gt;&gt; 角色管理 &gt;&gt; 设置功能菜单
						</td>
					</tr>
				</table>
			</div>
    	<div id="content_div" style="width: 100%; height: auto;min-height:535px;overflow:scroll;top:10px" class="list_div3">
    		<script type="text/javascript">
    				var d = new dTree('d','.','testForm');
    				<%=(String)request.getAttribute("treeStr") %>
    				document.write(d);
    		</script>
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
  </body>
</html>
