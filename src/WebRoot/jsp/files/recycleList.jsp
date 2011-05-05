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
		<title>回收站</title>
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
			//翻页操作
			function openPage(currentPage){
				document.getElementById("currentPage_param").value=currentPage;
				doSearch();
			}
			
			function doSearch(){
				var form = document.getElementById("queryForm1");
				form.action = "recycleAction!getRecycleList.action";
				form.submit();
			}
			
			function revertMore(){
				//获取选项
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				var param = "";
				var selectFlag = false;
				for (var i = 0; i < length; i++) {
					if (array[i].checked == true) {
						selectFlag = true;
						param +="&recycleIds="+array[i].value;
					}
				}
				
				if(selectFlag){
					if(confirm('确认要还原选中的记录吗？')){
						var form = document.getElementById("queryForm1");
						form.action = "recycleAction!revert.action?invalidate=0"+param;
						form.submit();
					}
				}else{
					alert('请选择要还原的记录!');
				}
			}
			function revert(id){
					if(confirm('确认要还原该记录吗？')){
						var form = document.getElementById("queryForm1");
						form.action = "recycleAction!revert.action?recycleIds="+id;
						form.submit();
					}
			}
			
			function clearRecycle(){
				if(confirm('确认要清空回收站中的信息吗?该操作将导致记录不可恢复!')){
						var form = document.getElementById("queryForm1");
						form.action = "recycleAction!clearAll.action";
						form.submit();
				}
			}
			
			function deleteRecycle(id){
				if(confirm('确认要删除该记录吗?该操作将导致记录不可恢复!')){
						var form = document.getElementById("queryForm1");
						form.action = "recycleAction!delete.action?recycleIds="+id;
						form.submit();
				}
			}
			
			function deleteMoreRecycle(){
				//获取选项
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				var param = "";
				var selectFlag = false;
				for (var i = 0; i < length; i++) {
					if (array[i].checked == true) {
						selectFlag = true;
						param +="&recycleIds="+array[i].value;
					}
				}
				
				if(selectFlag){
					if(confirm('确认要删除该记录吗?该操作将导致记录不可恢复!')){
						var form = document.getElementById("queryForm1");
						form.action = "recycleAction!delete.action?invalidate=0"+param;
						form.submit();
					}
				}else{
					alert('请选择要删除的记录!');
				}
			}
		</script>
	</head>
	<body class="body1">
		<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${pageVO.currentPage }" />
			<input type="hidden" name="fileName" id="fileName" value="${fileName }" />
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
							回收站
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="clearRecycle()">清空</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="deleteMoreRecycle()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="revertMore()">还原</a>
					</li>
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
								删除日期
							</th>
							<th>
								类型
							</th>
							<th>
								大小
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="r" items="${pageVO.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${r.id }" type="checkbox"></input>
								</td>
								<td id="fileName${r.id }" align="left">
									<c:if test="${r.isFolder==1}">
										<a href="fileAction!getMyFilesByParentId.action?parentId=${r.id }"><img src="${r.icon }"/>&nbsp;${r.displayName }</a>
									</c:if>
									<c:if test="${r.isFolder==0}">
										<a href="javascript:void(0);" onclick="showFileInfo('${r.id }')"><img src="${r.icon }"/>&nbsp;${r.displayName }</a>
									</c:if>
								</td>
								<td>
									${r.deleteTime }
								</td>
								<td>
									${r.fileType }
								</td>
								<td align="right">
									${r.fileSize }&nbsp;KB
								</td>
								<td align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteRecycle('${r.id }')">删除</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="revert('${r.id }')">还原</a>
								</td>
							</tr>
						</c:forEach>
				</table>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${pageVO.totalRows }</span> 条数据 当前第 <span class="font_red">${pageVO.currentPage }</span> 
                  		页 共 <span class="font_red">${pageVO.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${pageVO.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${pageVO.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${pageVO.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			<div class="bbar">
			</div>
		</div>
	</body>
</html>