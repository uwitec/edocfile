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
	<script type="text/javascript" src="tree-menu/dtree3.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	
	<script type="text/javascript">
		
		function do_close(){
			this.close();
		}
		
		//获取某个部门下的用户
		function getDeptUser(orgId){
			var orgId_ele = parent.frames("usermanagertbar").document.getElementById('orgId');
			orgId_ele.value = orgId;
			parent.frames("usermanagertbar").document.getElementById('userNameText').value="";
			var frames_ele = parent.frames("userlist_users");
			var forward = "";
			if(frames_ele!=null){
				var forward_ele = frames_ele.document.getElementById('forward');
				if(forward_ele!=null){
					forward = forward_ele.value;
				}
			}
			parent.document.all.userlist_users.src="userAction!getUsersByOrgId.action?orgId="+orgId+"&forward="+forward;
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
				    			if(v.parentMenuId=="-1"){
				    				d.add(v.id,v.parentId,v.deptName+"&nbsp;&nbsp;[&nbsp;&nbsp;<a href='javascript: d.openAll();'>打开</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='javascript: d.closeAll();'>收起</a>&nbsp;&nbsp;]");
				    			}
				    			d.add(v.id,v.parentId,v.deptName,"getDeptUser('"+v.id+"')" );
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
  
  <body>
    <div class="main">
    	<div id="content_div" class="" style="top:5px;">
    	</div>
    </div>
  </body>
  			<script type="text/javascript">
    				var d = new dTree('d');
					load_menu_tree(d);
    		</script>
</html>
