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
		<title>共享文件夹</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="js/files/files.js"></script>
		<!-- BEGIN Plugin Code -->
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.font_red{
			    color:#B9090C;
			}
			.file0 {
				position:relative;
				margin:5px 5px 5px 5px;
				width:243px;
				height:81px;
				z-index:1;
				float:left;
			}
			.file1 {
				position:relative;
				margin:5px 5px 5px 5px;
				width:243px;
				height:81px;
				z-index:1;
				background-image:url(images/ahover.jpg);
				float:left;
			}
			.file_inner{
				position:relative;
				left:6px;
				top:5px;
				width:230px;
				height:70px;
			}
			
			.file_content{
				position:relative;
				left:6px;
				top:5px;
				width:1000px;
				height:500px;
			}
		</style>
		<script type="text/javascript">
		var temp_obj;
			function cancelShore(id){		//撤销共享文件操作
				var parentId = document.getElementById("parentId").value;		//获取档案目录的上一级目录Id
				var form = document.getElementById("queryForm1");
				form.action = "fileAction!cancelShore.action?fileId="+id+"&Rnd="+Math.random();
				form.submit();
			}
			
			//文档预览
			function previewFile(id,version){
				window.open("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(),"","resizable=yes,status=no,toolbar=no,menubar=no,location=no");
				//showModalDialog("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(), "", "dialogWidth:1000px; dialogHeight:600px;help:no;scroll:no;status:no");
			}
			
			//查看文件详细信息
			function showFileInfo(id,fileName){
				var params = [];
				params[0] = fileName;
				showModalDialog("fileAction!getFileInfo.action?sourceFileId="+id+"&Rnd="+Math.random(), params, "dialogWidth:1000px; dialogHeight:600px;help:no;scroll:no;status:no");
			}
			
			function searchFile(){
				//重新刷新页面
				var form = document.getElementById("queryForm1");
				form.action = "fileAction!getShoredFiles.action?Rnd="+Math.random();
				form.submit();
			}
			
			//展示方式的改变
			function changeLayout(type){
				document.getElementById('layoutStyle').value=type;	
				
				var form = document.getElementById("queryForm1");
				form.action = "fileAction!getShoredFiles.action?Rnd="+Math.random();
				form.submit();
			}
			
			function mouse_over(obj){
				if(temp_obj){
					temp_obj.className="file0";
					
					temp_obj = obj;
				}else{
					temp_obj = obj;
				}
				obj.className="file1";
			}
			
			function mouse_out(obj){
				//obj.className="file0";
			}
		</script>
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="1" />
			<input type="hidden" name="parentId" id="parentId" value="${parentId }" />
			<input type="hidden" name="layoutStyle" id="layoutStyle" value="${layoutStyle }" />
		<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>
							<c:forEach var="v" items="${mulus}">
							<a href="fileAction!getShoredFiles.action?parentId=${v.id }">${v.fileName } </a>
							<c:choose>
							<c:when test="${v.id != parentId}">
							&gt;&gt;
							</c:when> 
							</c:choose>
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="changeLayout(1)"><img src="icon/sjgl.gif"/>&nbsp;平铺</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="changeLayout(0)"><img src="icon/scdh.gif"/>&nbsp;列表</a>
					</li>
					<li style="width:230px;">
							<input id="fileName" name="fileName" value="${fileName }" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchFile()" type="button" style="line-height:18px;" value="查询"  class="button1"  />
					</li>
				</ul>
			</div>
			<div class="content">
			<c:forEach var="edocFile" items="${filePageVO.result}">
			<div id="file_div" class="file0" onMouseOver="mouse_over(this)" onMouseOut="mouse_out(this)">
				<div class="file_inner">
				  <table width="230px" height="70px" border="0" cellspacing="0">
					<tr>
					  <td>
					  	<div style="width:230px;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;" title="${edocFile.fileName }">
					  	&nbsp;&nbsp;
					  	<c:if test="${edocFile.isFolder==1}">
							<a href="fileAction!getShoredFiles.action?parentId=${edocFile.id }"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>
						</c:if>
						<c:if test="${edocFile.isFolder==0}">
							<a href="javascript:void(0);" onclick="showFileInfo('${edocFile.sourceFileId }','${edocFile.fileName }')"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>
						</c:if>
					  	<input id="sourceFileName${edocFile.id }" type="hidden" value="${edocFile.fileName }" >
					  	</div>
					  </td>
				    </tr>
					<tr>
					  <td>&nbsp;&nbsp;&nbsp;当前版本：${edocFile.currentVersion }</td>
				    </tr>
					<tr>
					  <td>&nbsp;&nbsp;&nbsp;
					  	<c:if test="${edocFile.shoreUserId==DOCUSER.id}"><!-- 只有文档创建人才能执行撤销共享操作 -->
							<a href="javascript:void(0);" onclick="cancelShore('${edocFile.sourceFileId }')">撤销共享</a>&nbsp;&nbsp;
						</c:if>
						<c:if test="${edocFile.isFolder==0}"><!-- 除文件夹以外的所有文档都有预览和下载的操作 -->
							<c:if test="${edocFile.perView==1 || edocFile.shoreUserId==DOCUSER.id}">
								<a href="javascript:void(0);" onclick="previewFile('${edocFile.sourceFileId }','${edocFile.currentVersion }')">预览</a>&nbsp;&nbsp;
							</c:if>
							<c:if test="${edocFile.perDownLoad==1 || edocFile.shoreUserId==DOCUSER.id}">
								<a href="fileAction!downLoadFile.action?sourceFileId=${edocFile.sourceFileId }">下载</a>&nbsp;&nbsp;
							</c:if>
						</c:if>
					  </td>
				    </tr>
				  </table>
			  </div>
			</div>
			</c:forEach>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${filePageVO.totalRows }</span> 条数据 当前第 <span class="font_red">${filePageVO.currentPage }</span> 
                  		页 共 <span class="font_red">${filePageVO.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${filePageVO.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${filePageVO.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${filePageVO.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			<div class="bbar">
			</div>
		</div>
		</form>
	</body>
</html>