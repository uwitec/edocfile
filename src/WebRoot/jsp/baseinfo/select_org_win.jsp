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
		<title>选择科室</title>
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="tree-menu/dtree2.css">
		<script type="text/javascript" src="tree-menu/dtree2.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript">
		function do_close(){
			this.close();
		}
		
		function select(orgId,orgName){
			var return_value = [];
			return_value[0] = orgId;
			return_value[1] = orgName;
			window.returnValue=return_value; 
			window.close();
		}
		
		function load_menu_tree(d){
			var content_div = document.getElementById('content_div');
			if(content_div!=null){
				$.ajax({
				    url:'orgAction!getAllOrgs.action?printJSON=true', 
				    type:'post',
				    error: function(){
				    	return false;
				    },
				    success: function(json){
				    	if(json!=null && json!=""){
				    		var rs = eval('(' + json + ')');	//解析返回的JSON格式数据
				    		$.each(rs,function(i, v){
				    			//if(v.parentId=="-1"){
				    			//	d.add(v.id,v.parentId,v.deptName+"&nbsp;&nbsp;[&nbsp;&nbsp;<a href='javascript: d.openAll();'>打开</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='javascript: d.closeAll();'>收起</a>&nbsp;&nbsp;]","select('"+v.id+"','"+v.deptName+"')");
				    			//}
				    			d.add(v.id,v.parentId,v.deptName,"select('"+v.id+"','"+v.deptName+"')" );
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

	<body class="body2">
		<div
			style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
			<div
				style="width: 100%; height: 5%; position: relative; float: left; top: 0px;">
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
			<div id="content_div" style="width: 100%; height: auto; min-height: 505px; overflow: scroll; top: 10px" class="list_div3">
			</div>
			<div style="width: 100%; height: 5%; position: relative; float: left;" class="list_btline">
				<table width="100%" align="center" cellspacing="0" cellpadding="2">
					<tr>
						<td align="center" height="24">
							<input type="button" class="button1" onclick="do_close()" value="关闭" />
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
