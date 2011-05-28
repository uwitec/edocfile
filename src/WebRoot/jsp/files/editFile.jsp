<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
	String fileName = (String)request.getAttribute("fileName");
	
	String filePath = basePath + "temp/" + fileName;
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看原文</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<script type="text/javascript" src="js/office_edit.js"></script>
	<style type="text/css">
			.office_edit {
				position:relative;
				margin:0 auto;
				width:100%;
				height:660;
				border:1px solid #7fa9e4;
				z-index:1;
			}
			
			.office_edit_tools {
				position:relative;
				left:1%;
				top:5px;
				width:98%;
				height:24px;
				z-index:1;
				background:url(images/office_edit_tools_bg.gif) repeat;
				border:1px solid #99bbe8;
				float:left;
				line-height:24px;
			}
			
			.office_edit_doc {
				position:relative;
				left:1%;
				top:10px;
				width:98%;
				height:600;
				z-index:1;
				border:1px solid #99bbe8;
				float:left;
			}
			.offiec_button{
				position:relative;
			    border:0 none;
			    background:transparent;
			    padding-left:10px;
			    padding-right:10px;
			    overflow:visible;
			    width:auto;
			    -moz-outline:0 none;
			    outline:0 none;
				border:1px solid #7fa9e4;
			}
			.STYLE1 {
				font-size: 12px;
				font-weight: bold;
				font-family: "宋体";
			}
	</style>
	<script type="text/javascript">
		function init(){
			var webObj=document.getElementById("edocfile-office");
			//隐藏工具栏
			try{
				webObj.ShowToolBar = 0;
				//加载文档
				var fileSuffix = document.getElementById('fileSuffix').value;
				var filePath = document.getElementById('filePath').value;
				webObj.LoadOriginalFile(filePath, fileSuffix);	
			
			
				webObj.SetCurrUserName('陈超');
                webObj.SetTrackRevisions(1);                   //处于修订状态
                webObj.ShowRevisions(0);                         //隐藏修订信息

				
			}catch(e){
				alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
			}	
			
			
			
			//webObj.SetToolBarButton2("Menu Bar",2,0);
		}
	</script>
  </head>
  
 <body onload="init()">
  <form action="" method="post">
	<input id="sourceFileId" type="hidden" value="${fileVersion.edocFileId }"/>
	<input id="fileSuffix" type="hidden" value="${fileVersion.fileSuffix }"/>
	<input id="filePath" type="hidden" value="<%=filePath %>" />
	<input id="fileName" type="hidden" value="${fileVersion.fileName }" />
  </form>

 <div class="office_edit">
  <div class="office_edit_tools">
  	<div style="position: relative;vertical-align: middle;display: table-cell;float:left;padding-left:10px;">
		<span class="STYLE1">当前文件:</span>	<span style="font-size: 12px;font-family: "宋体";">${fileVersion.fileName }</span>
	</div>
  	<div style="position: relative;top: 10%;vertical-align: middle;display: table-cell;float:right;padding-right:10px;">
  	<input type="button" class="offiec_button" onclick="save_office()" value="保存" />
	<!-- <input type="button" class="offiec_button" onclick="edit_office()" value="编辑" />  -->
	<input type="button" class="offiec_button" onclick="show_office_editinfo()" value="显示修订" />
	<input type="button" class="offiec_button" onclick="hidden_office_editinfo()" value="隐藏修订" />
	<input type="button" class="offiec_button" onclick="exit_office()" value="退出" />
	</div>
  </div>
  <div class="office_edit_doc">
  	<object id="edocfile-office" height="610" width='100%' style="top: 5px" classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase="<%=basePath %>sfile/edocfile-office.cab">
  		<param name='_ExtentX' value='6350'>
  		<param name='_ExtentY' value='6350'>
  	</object>
  </div>
</div>
</body>
</html>
