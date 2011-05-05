<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>选择菜单</title>
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="tree-menu/dtree2.css">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="tree-menu/dtree2.js"></script>
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
		
		function do_close(){
			this.close();
		}
		
		function select(id,name){
			var return_value = [];
			return_value[0] = id;
			return_value[1] = name;
			window.returnValue=return_value; 
			window.close();
		}
		
		function load_menu_tree(d){
			var content_div = document.getElementById('content_div');
			if(content_div!=null){
				$.ajax({
				    url:'menuAction!findAllMenus.action?printJSON=true', 
				    type:'post',
				    error: function(){
				    	return false;
				    },
				    success: function(json){
				    	if(json!=null && json!=""){
				    		var rs = eval('(' + json + ')');	//解析返回的JSON格式数据
				    		$.each(rs,function(i, v){
				    			if(v.parentMenuId=="-1"){
				    				d.add(v.id,v.parentMenuId,v.name+"&nbsp;&nbsp;[&nbsp;&nbsp;<a href='javascript: d.openAll();'>打开</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='javascript: d.closeAll();'>收起</a>&nbsp;&nbsp;]","select('"+v.id+"','"+v.name+"')");
				    			}
				    			d.add(v.id,v.parentMenuId,v.name,"select('"+v.id+"','"+v.name+"')");
				    		});
							
				    		var content_html = d.toString();
				    		content_div.innerHTML = content_html;
				    	}else{
				    		return;
				    	}
				   }
			   	});
			}
		}
	</script>
  </head>
  
  <body class="body1">
     <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
    	<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>系统管理 &gt;&gt; 角色管理 &gt;&gt; 设置权限
						</td>
					</tr>
				</table>
			</div>
    	<div id="content_div" style="width: 100%; height: auto;min-height:540px;overflow:scroll;" class="list_div3">
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
  			<script type="text/javascript">
    				var d = new dTree('d');
					load_menu_tree(d);
    		</script>
</html>
