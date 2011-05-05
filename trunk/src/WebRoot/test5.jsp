<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String script = "";
script = (String)request.getAttribute("treeScript");
if(script==null){
	script = "";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>

<title></title>
<!-- The xtree script file -->
<script src="jsplugin/xtree2b/js/xtree2.js"></script>
<script src="jsplugin/xtree2b/js/xmlextras.js"></script>
<script src="jsplugin/xtree2b/js/xloadtree2.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>

<!-- Modify this file to change the way the tree looks -->
<link type="text/css" rel="stylesheet" href="jsplugin/xtree2b/css/xtree2.css">

<script type="text/javascript">
	function init(){
		$.ajax({
				    url:'menuAction!createMenuTree.action?Rnd=' + Math.random(), 
				    type:'post',
				    error: function(){
				    	return false;
				    },
				    success: function(script){
				    	var menuDiv = document.getElementById("menuDiv");
				    	var str = "";
				    	str += "<script type='text/javascript'>";
				    	str += script;
				    	str += "<//script>";
				    	menuDiv.innerHTML = str;
				   }
			   	});
	}
</script>

</head>
<body>

<div id="menuDiv" style="position: absolute; width: 200px; top: 0px; left: 0px; height: 100%; padding: 5px; overflow: auto;">
	<script type="text/javascript">
		<%=script %>
	</script>
</div>


</body>
</html>
