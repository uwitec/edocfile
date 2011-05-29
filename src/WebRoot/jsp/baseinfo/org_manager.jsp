<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<base href="<%=basePath%>">
		<title>科室管理</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="tree-menu/dtree.css">
		<script type="text/javascript" src="tree-menu/dtree4.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/baseinfo/org.js"></script>
		<script type="text/javascript">
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
				    			if(v.parentMenuId=="-1"){
				    				d.add(v.id,v.parentId,v.deptName+"&nbsp;&nbsp;[&nbsp;&nbsp;<a href='javascript: d.openAll();'>打开</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='javascript: d.closeAll();'>收起</a>&nbsp;&nbsp;]");
				    			}
				    			d.add(v.id,v.parentId,v.deptName,"selectOrg('"+v.id+"')" );
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
		
		function selectOrg(orgId){
		
		}
		
		function deleteOrg(){
			var deptId = d.getSelected();
			
			$.ajax({
				    url:"orgAction!deleteOrg.action?orgId="+deptId+"&Rnd="+Math.random(), 
				    type:'post',
				    error: function(){
				    	return false;
				    },
				    success: function(json){
				    	if(json){
				    		alert(json);
				    	}
				    	window.location.reload();
				   	}
			 });
			   	
			//var form=document.getElementById('form1');
			//form.action = "orgAction!deleteOrg.action?orgId="+deptId+"&Rnd="+Math.random();
			//form.submit();
		}
		function updateOrg(){
			//alert(d.getSelected());
			var deptId = d.getSelected();
			showModalDialog("orgAction!beforeUpdate.action?deptId="+deptId+"&Rnd="+Math.random(), "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
			window.location.reload();
		}
		</script>
		<!-- BEGIN Plugin Code -->
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
		</style>
	</head>
	<body class="body1">
		<form id="form1" action="" method="POST">
		
		</form>
		<input id="orgId" type="hidden" />
		<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>基础数据 &gt;&gt; 科室信息管理
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="showAddOrgWin()"><img src="icon/add.png"/>&nbsp;新建</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="updateOrg()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteOrg()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
			</div>
			<div id="content_div" class="content" style="top:5px;">
				
			</div>
			<div class="bbar">
			</div>
		</div>
	</body>
	<script type="text/javascript">
    				var d = new dTree('d');
					load_menu_tree(d);
    </script>
</html>