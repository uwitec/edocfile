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
    <title>上传文件</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="tree-table/stylesheets/master.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/baseinfo/user.js"></script>
	<script type="text/javascript" src="js/Validator.js"></script>
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
		.inner_contents{
			width:100%;
			position:relative;
			float:left;
			height:346px;
		}
		.t_bar{
			width:100%;
			height:25px;
			position:relative;
			float:left;
			border-bottom: 1px solid #a6c5e4;
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
		.form_table{}
		.form_table tr{}
		.form_table tr td{
			line-height:25px !important;
			text-indent:10px;
			FONT-SIZE: 12px;
			color:#15428b;
		}
		IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
	</style>
	<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSubmit(){
			var form=document.getElementById('save_form');
			if(form.fireEvent('onsubmit')){
				form.submit();
			}
			window.close();		//关闭窗口
		}
		
		function addFile(){
			showModalDialog("addfile_win.jsp", "", "dialogWidth:500px; dialogHeight:300px; status:0; help:0");
		}
		
	</script>
  </head>
  
  <body>
    <div class="area">
    	<div class="title">
    		上传文件
    	</div>
    	<form id="save_form" action="fileAction!createFolder.action" onsubmit="return Validator.Validate(this,3)" method="post">
    	<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="addFile()"><img src="icon/add.png"/>&nbsp;添加</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteFile()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
		</div>
    	<div class="inner_contents">
    		<table class="example" id="dnd-example">
					<thead>
						<tr>
							<th>
								
							</th>
							<th>
								名称
							</th>
							<th>
								备注
							</th>
						</tr>
					</thead>
					<tbody id="file_list_tbody">
					
					</tbody>
				</table>
    		
    	</div>
    	<input id="parentFolderId" type="hidden" name="edocFile.parentId" value="-1"/>
    	</form>
    	<div class="b_bar">
    		<input type="button" class="button1" onclick="doSubmit()" value="上传"/>
    		<input type="button" class="button1" onclick="doClose()" value="关闭"/>
    	</div>
    </div>
  </body>
</html>
