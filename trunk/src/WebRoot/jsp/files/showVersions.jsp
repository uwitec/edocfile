<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<base target="_self">
		<title>添加文件</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/baseinfo/user.js"></script>
		<script type="text/javascript" src="js/Validator.js"></script>
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
    function closeWin(){
    	window.close();
    }
    
    function addFileVersion(){
    		var params = [];
			params[0] = document.getElementById('sourceFileId').value;
			params[1] = document.getElementById('fileName').value;
			var returnValue = showModalDialog("jsp/files/addFileVersion.jsp", params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
			if(returnValue){
				doSearch();
			}
    }
    
    function init(){
    	var params = window.dialogArguments;	//参数为上一级文件夹的Id
    	document.getElementById('fileName').value=params[0];
    }
    
	</script>
	</head>

	<body class="body2" onload="init()">
		<input type="hidden" name="fileName" id="fileName" />
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${fileVersions.currentPage }" />
			<input type="hidden" name="sourceFileId" id="sourceFileId" value="${sourceFileId }" />
		</form>
		<div class="area">
			<div style="width: 100%; height:6%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>系统管理 &gt;&gt; 查看版本信息
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="addFileVersion()"><img src="icon/add.png"/>新增版本</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th width="1%"><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th width="20%">
								名称
							</th>
							<th width="10%">
								修改时间
							</th>
							<th width="10%">
								当前版本
							</th>
							<th width="10%">
								操作
							</th>
						</tr>
						<c:forEach var="v" items="${fileVersions.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${v.id }" type="checkbox"></input>
								</td>
								<td>
									<img src="${v.icon }"/>&nbsp;${v.fileName }
								</td>
								<td>
									${v.updateTime }
								</td>
								<td>
									${v.version }
								</td>
								<td align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" >共享</a>
									&nbsp;&nbsp;<a href="javascript:void(0);">删除</a>
								</td>
							</tr>
						</c:forEach>
				</table>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${fileVersions.totalRows }</span> 条数据 当前第 <span class="font_red">${fileVersions.currentPage }</span> 
                  		页 共 <span class="font_red">${fileVersions.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${fileVersions.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${fileVersions.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${fileVersions.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			<div class="bbar">
			</div>
		</div>
	</body>
</html>

