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
		<script type="text/javascript" src="js/office_edit.js"></script>
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
			<input type="hidden" name="currentPage" id="currentPage_param" value="${filePageVO.currentPage }" />
			<input type="hidden" name="parentId" id="parentId" value="${parentId }" />
			<input type="hidden" name="layoutStyle" id="layoutStyle" value="${layoutStyle }" />
		<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>
							<c:forEach var="v" items="${mulus}">
							<a href="javascript:void(0);" onclick="openFolder('${v.id }')">${v.fileName } </a>
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
						<a href="javascript:void(0);" onclick="showUploadWin()"><img src="icon/upload.png"/>&nbsp;上传</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="copyOrCatFiles(0)"><img src="icon/copy.png"/>&nbsp;复制</a>
					</li>
						<li>
						<a href="javascript:void(0);" onclick="copyOrCatFiles(1)"><img src="icon/cut.gif"/>&nbsp;剪切</a>
					</li>
						<li>
						<a href="javascript:void(0);" onclick="pasteFiles()"><img src="icon/paste.png"/>&nbsp;粘贴</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="changeLayout(1)"><img src="icon/sjgl.gif"/>&nbsp;平铺</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="changeLayout(0)"><img src="icon/scdh.gif"/>&nbsp;列表</a>
					</li>
					 <li style="width:230px;">
							<input id="fileName" name="fileName" value="${fileName }" type="text" style="line-height:18px;" class="inputText" onmouseover="this.style.borderColor='#99E300'" onmouseout="this.style.borderColor='#A1BCA3'" /> <input onclick="searchFile()" type="button" style="line-height:18px;" value="查询" class="button1" />
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
					  	<input name="checkItem" value="${edocFile.id }" type="checkbox"></input>
					  	<c:if test="${edocFile.isFolder==1}">
							<a href="javascript:void(0);" onclick="openFolder('${edocFile.id }')"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>						
						</c:if>
						<c:if test="${edocFile.isFolder==0}">
							<a href="javascript:void(0);" onclick="showFileInfo('${edocFile.id }')"><img src="${edocFile.icon }"/>&nbsp;${edocFile.fileName }</a>						
						</c:if>
					  	<input id="sourceFileName${edocFile.id }" type="hidden" value="${edocFile.fileName }" >
					  	</div>
					  </td>
				    </tr>
					<tr>
					  <td>
					  <c:if test="${edocFile.isFolder==0}">
					  &nbsp;&nbsp;当前版本：${edocFile.currentVersion }
					  </c:if>
					  <c:if test="${edocFile.isShored==1}" >
						<font color="red">【已共享】</font>
					  </c:if>
					  </td>
				    </tr>
					<tr>
					  <td>
					  <c:if test="${edocFile.isFolder==0}">
					  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="previewFile('${edocFile.id }','${edocFile.currentVersion }')">预览</a>
					  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="before_edit_office_open_newwin('${edocFile.id }','${DOCUSER.id }','${edocFile.fileSuffix }','${edocFile.currentVersion }')">编辑</a>
					  </c:if>
					  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="showShoreFileWin('${edocFile.id }',${edocFile.isShored})">共享</a> 
					  <c:if test="${edocFile.isShored==1}" >
					  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="cancelShore('${edocFile.id }')">撤销共享</a>&nbsp;&nbsp;
					  </c:if>
					  &nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteOne('${edocFile.id }')">删除</a>		
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