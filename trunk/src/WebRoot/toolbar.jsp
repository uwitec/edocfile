<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>pro_dropdown_2/pro_dropdown_2.css" />

	<script src="<%=basePath%>pro_dropdown_2/stuHover.js" type="text/javascript"></script>
	
	<style type="text/css">
		HTML { 
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
			border:0px;
		 }
		
		BODY {
			background:#e4f0f9;
			border:0px;
			text:white;
			height:100%;
		}
		IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
		.button1{
			font:normal 12px/120% Verdana,'宋体';height:20px;
			border-left:1px #ced4e2 solid;
			border-top:1px #ced4e2 solid;
			border-right:1px #888888 solid;
			border-bottom:1px #888888 solid;
			background:	#dddddd;
			margin-left:10px;
			margin-right:10px;
			padding-left:10px;
			padding-right:10px;
		}
		.div_1{
				position: relative;
				width: 100%;
				float: left;
		}
		.meun_1{
				position: relative;
				float: left;
				width: 65%;
				text-align:right;
		}
		.search{
				position: relative;
				
				float: left;
		}
		td{
			color:#1060A4;
			font-size:12px;
			font-weight: bold;
			line-height:23px;
		}
		a:link{text-decoration:none;color:#1060A4;} 　　
		a:hover{text-decoration:none;color:#1060A4;} 　　
		a:visited{text-decoration:none;color:#1060A4;} 
	</style>  
	<script type="text/javascript">
		function searchDocument(){
			var form = document.getElementById('searchDocumentForm');
			form.target = "perspective_content";
			form.submit();
		}
	</script>     
 </head>
  
<body>
	<div class="div_1">
		<div class="meun_1">
			<table >
				<tr height="25">
					<td width="54%" align="right" nowrap>
		           	 	<a href="workSpace.jsp" target="perspective_content">我的工作台</a>
		            </td>
				</tr>
			</table>
		</div>
		<div class="search">
			<table width="100%">
				<tr height="25">
				    <td width="46%" align="right" valign="middle" nowrap>
		            <form id="searchDocumentForm" action="searchAction!documentSearch.action" method="post">
						
		            <input type="text" name="keyWord" size="50"/>
			        <input type="button" class="button1" onClick="searchDocument()" value="全文检索"/>
		            </form>	
		            </td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>