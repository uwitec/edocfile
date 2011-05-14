<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/comm.css" rel="stylesheet" type="text/css" />
	<link href="css/tbar.css" rel="stylesheet" type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
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
		function selectAllCheckbox(){
				var flag = document.getElementById("selectAll");
				var array = document.getElementsByName("checkItem");
				var length = array.length;
				if (flag.checked == true) {
					for (var i = 0; i < length; i++) {
						array[i].checked = true;
					}
				} else {
					for (var i = 0; i < length; i++) {
						array[i].checked = false;
					}
				}
			}
		function writeMsg(){
			showModalDialog("jsp/message/writeMsg.jsp?&Rnd="+Math.random(), "", "dialogWidth:650px; dialogHeight:400px;help:no;scroll:no;status:no");
			doSearch();
		}
		
		function doSearch(){
			var form = document.getElementById('form1');
			form.action  = "messageAction!getSendMessages.action?Rnd="+Math.random();
			form.submit();
		}
		
		function showSendMsg(id){
			showModalDialog("messageAction!showSendMsg.action?sendMsgId="+id+"&Rnd="+Math.random(), "", "dialogWidth:650px; dialogHeight:400px;help:no;scroll:no;status:no");
			doSearch();
		}
		
		function deleteSendMsg(id){
			var form = document.getElementById('form1');
			form.action  = "messageAction!deleteSendMessages.action?sendMsgIds="+id+"&Rnd="+Math.random();
			form.submit();
		}
		//删除多条记录
		function deleteMore(){
			var array = document.getElementsByName("checkItem");
			var length = array.length;
			var param = "";
			var selectFlag = false;
			for (var i = 0; i < length; i++) {
				if (array[i].checked == true) {
					selectFlag = true;
					param +="&sendMsgIds="+array[i].value;
				}
			}
			if(selectFlag){
				var form = document.getElementById("form1");
				form.action = "messageAction!deleteSendMessages.action?Rnd="+Math.random()+param;
				form.submit();
				
			}else{
				alert('请选择要删除的记录!');
			}
		}
	</script>
  </head>
  
  <body class="body1">
  	<form id="form1" action="" method="post">
		<input type="hidden" name="currentPage" id="currentPage_param" value="${filePageVO.currentPage }" />
		<input type="hidden" name="msgType" id="msgType" value="${msgType }" />
		<input type="hidden" name="sendFlag" value="1" />
    <div class="area">
			<div style="width: 100%; height:5%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="33">
						</td>
						<td>
							<strong>您当前的位置：</strong>
							短消息管理&gt;&gt;
							<c:if test="${msgType==1}">
								已发送
							</c:if>
							<c:if test="${msgType==2}">
								待发送
							</c:if>
						</td>
					</tr>
				</table>
			</div>
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="writeMsg()"><img src="icon/add.png"/>&nbsp;撰写</a>
					</li>
					<!-- 
					<li>
						<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					 -->
					<li>
						<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
					
				</ul>
			</div>
			<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								主题
							</th>
							<th>
								发件人
							</th>
							<th>
								发送时间
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="msg" items="${filePageVO.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${msg.id }" type="checkbox"></input>
								</td>
								<td align="left">
									
									<a href="javascript:void(0);" onclick="showSendMsg('${msg.id }');">${msg.title }</a>
								</td>
								<td>
									${msg.fromUserName }
								</td>
								<td>
									${msg.createDate }
								</td>
								<td align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showSendMsg('${msg.id }');">查看</a>
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteSendMsg('${msg.id }');">删除</a>
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
	</form>
  </body>
</html>
