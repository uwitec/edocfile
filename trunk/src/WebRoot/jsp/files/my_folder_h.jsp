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
		<title>我的文件夹</title>
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
		</style>
		<script type="text/javascript">
			//共享文件(文件夹)
			function showShoreFileWin(id,isShored){
				//if(isShored == 1){
				//	alert("该文件已共享!");
				//	return;
				//}
				var args = [];
				args[0] = id;
					
				//var fileName = document.getElementById("fileName"+id).innerHTML;
				var fileName = document.getElementById("sourceFileName"+id).value;
				args[1] = fileName;
				
				var parentId = document.getElementById('parentId').value;
				args[2] = parentId;
				
				var params = [];
				params[0] = args;
				//showModalDialog("jsp/files/shore_file_win.jsp", params, "dialogWidth:800px; dialogHeight:500px;help:no;scroll:no;status:no");
				
				showModalDialog("fileAction!beforeShoreFile.action?sourceFileId="+id, params, "dialogWidth:900px; dialogHeight:500px;help:no;scroll:no;status:no");
				//重新刷新页面
				var form = document.getElementById("queryForm1");
				form.action = "fileAction!getMyFilesByParentId.action";
				form.submit();
			}
			
			function showVersion(id,fileName){
				var params = [];
				params[0] = fileName;
				showModalDialog("fileAction!getEdocFileVersions.action?sourceFileId="+id, params, "dialogWidth:1000px; dialogHeight:500px;help:no;scroll:no;status:no");
				//showModalDialog("jsp/files/show_version.jsp", "", "dialogWidth:1000px; dialogHeight:500px;help:no;scroll:no;status:no");
			}
			
			function showFileInfo(id){
				showModalDialog("fileAction!getFileInfo.action?sourceFileId="+id, "", "dialogWidth:1000px; dialogHeight:600px;help:no;scroll:no;status:no");
			}
		</script>
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${filePageVO.currentPage }" />
			<input type="hidden" name="parentId" id="parentId" value="${parentId }" />
		</form>
		<div class="area">
			<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>
							<c:forEach var="v" items="${mulus}">
							${v.fileName } 
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
						<a href="javascript:void(0);" onclick="showAddFolderWin()"><img src="icon/add.png"/>&nbsp;新目录</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="showUploadWin()"><img src="icon/upload.png"/>&nbsp;上传</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="showUploadWin()"><img src="icon/sjgl.gif"/>&nbsp;平铺</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="showUploadWin()"><img src="icon/scdh.gif"/>&nbsp;列表</a>
					</li>
					<!-- 
					<li>
						<a href="javascript:void(0);" onclick="showUploadWin()"><img src="icon/shore.png"/>&nbsp;共享</a>
					</li>
					 -->
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								名称
							</th>
							<th>
								修改日期
							</th>
							<th>
								类型
							</th>
							<th>
								大小
							</th>
							<th>
								当前版本
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="edocFile" items="${filePageVO.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${edocFile.id }" type="checkbox"></input>
								</td>
								<td id="fileName${edocFile.id }" align="left">
									<c:if test="${edocFile.isFolder==1}">
										<a href="fileAction!getMyFilesByParentId.action?parentId=${edocFile.id }"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>
									</c:if>
									<c:if test="${edocFile.isFolder==0}">
										<a href="javascript:void(0);" onclick="showFileInfo('${edocFile.id }')"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>
									</c:if>
									<c:if test="${edocFile.isShored==1}" >
										<font color="red">【已共享】</font>
									</c:if>
									<input id="sourceFileName${edocFile.id }" type="hidden" value="${edocFile.fileName }" >
								</td>
								<td>
									${edocFile.updateTime }
								</td>
								<td>
									${edocFile.fileType }
								</td>
								<td align="right">
									${edocFile.fileSize }&nbsp;KB
								</td>
								<td align="right">
									<a href="javascript:void(0);" onclick="showVersion('${edocFile.id }','${edocFile.fileName }')">1.0</a>
								</td>
								<td align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showShoreFileWin('${edocFile.id }',${edocFile.isShored})">共享</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteOne('${edocFile.id }')">删除</a>
								</td>
							</tr>
						</c:forEach>
				</table>
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
	</body>
</html>