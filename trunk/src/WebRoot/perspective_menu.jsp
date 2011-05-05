<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String script = "";
script = (String)request.getAttribute("treeScript");
if(script==null){
	script = "";
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<script src="jsplugin/xtree2b/js/xtree2.js"></script>
		<script src="jsplugin/xtree2b/js/xmlextras.js"></script>
		<script src="jsplugin/xtree2b/js/xloadtree2.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		
		<link type="text/css" rel="stylesheet" href="jsplugin/xtree2b/css/xtree2.css">
				
		<script type="text/javascript"></script>
		<style type="text/css">
			body,html {
			margin:0;
			width:100%;
			height:100%
			}
			.menu_content{
				left:0px;
				top:0px;
				margin:0 auto;
				width:100%;
				height:100%;
				border-left: 1px solid #a6c5e4;
				border-top: 1px solid #a6c5e4;
				float:left;
			}
			.nav_title{
				width:100%;
				height:26px;
				background:#e4f0f9;
				text-align:left;
				line-height:26px !important;
				text-indent:10px;
				FONT-SIZE: 12px;
				color:#15428b;
			    font-weight:bold;
				float:left;
				border-bottom: 1px solid #7b9ebd;
			}
			.nav{
				width:100%;
				top:5px;
				left:15px;
				height:440px;
				overflow:auto;
				float:left;
			}
			.img2 {
				border-width: 0px;
				margin-bottom:-3px;
			}
			a:visited{text-decoration:none;color:#15428b;}
		</style>
		<script type="text/javascript">
			function link(url,target){
				if(!url){
					return;
				}
				var indexed = url.indexOf("?");
				if(indexed>=0){
					url = url + "&Rnd="+Math.random();
				}else{
					url = url + "?Rnd="+Math.random();
				}
				parent.document.all.perspective_content.src = url;
			}
			function refresh(){
				window.location.reload();
			}
		</script>
	</head>
	
	<body>
		<div class="menu_content">
			<div class="nav_title">
				<table width="100%">
					<tr>
						<td align="left">
							<span><img class="img2" src='./icon/nav.gif' border="0">功能导航</span>
						</td>
						<td align="right">
							<a href="javascript:void(0);" onclick="refresh();"><span><img class="img2" src='./images/refresh.gif' border="0">刷新</span></a>
						</td>
					</tr>
				</table>
			</div>
			<div class="nav">
				<script type="text/javascript">
					<%= script %>
				</script>
			</div>
		</div>
	</body>
	
</html>
