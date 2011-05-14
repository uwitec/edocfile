<%@ page language="java" pageEncoding="UTF-8"%>
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
    <base target="_self">
    <title>写短消息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/Validator.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		function doClose(){
			this.close();
		}
		function doSend(){
			document.getElementById('msgState').value=1;
			var receiverIds = document.getElementById("receiverIds").value;
			if(!receiverIds){
				alert("请选择接收人信息!");
				return;
			}
			var form = document.getElementById('save_form');
			form.action  = "messageAction!saveMessage.action?Rnd="+Math.random();
			form.submit();
		}
		function doSave(){
			document.getElementById('msgState').value=0;
			
			var form = document.getElementById('save_form');
			form.action  = "messageAction!saveMessage.action?Rnd="+Math.random();
			form.submit();
		}
		
		function selectReceiver(){
			var  returnValue = showModalDialog("jsp/baseinfo/select_users.jsp", "", "dialogWidth:800px; dialogHeight:500px; status:0; help:0");
			if(returnValue!=null && returnValue.length>0){
	    		var table = document.getElementById("userTable");
	    		var html = "";
	    		var paramLength = returnValue.length;
	    		var receiverIds = "";
	    		var receiverNames = "";
	    		for(var i=0;i<paramLength;i++){
	    			var args = returnValue[i];
	    			receiverIds+=args[0]+",";
	    			receiverNames+=args[1]+",";
	    		}
	    		
	    		receiverIds = receiverIds.substring(0,receiverIds.lastIndexOf(","));
	    		receiverNames = receiverNames.substring(0,receiverNames.lastIndexOf(","));
	    		document.getElementById('receiverIds').value=receiverIds;
	    		document.getElementById('receiverNames').value=receiverNames;
    		}	
		}
	</script>
  </head>
  
  <body class="body1">
    <form id="save_form" action="" method="POST">
    <input type="hidden" name="message.id" value="${sendMsg.id }" />
    <input type="hidden" name="message.fromUserName" value="${DOCUSER.trueName }" />
    <input type="hidden" name="message.fromUserId" value="${DOCUSER.id }" />
    <input type="hidden" id="msgState" name="message.state" value="0" />
   <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>短消息管理 &gt;&gt; 写短消息
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 330px;" class="list_div3">
	    	<table width="100%" >
	    		<tr>
	    			<td align="right" width="10%">标题：</td>
	    			<td width="70%"><input  type="text" size="50" name="message.title" value="${sendMsg.title }"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right" width="10%">接收人：</td>
	    			<td width="70%">
	    			<input id="receiverNames" name="receiverNames" type="text" size="50" value="${sendMsg.receiverNames }" readonly/>
	    			&nbsp;&nbsp;
					<img src="icon/find.png" onclick="selectReceiver()" />
	    			<input id="receiverIds" name="receiverIds"  type="hidden" value="${sendMsg.receiverIds }"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td align="right" width="10%">正文：</td>
	    			<td width="70%">
	    				<textarea name="message.content" rows="10" cols="60">${sendMsg.content }</textarea>
	    			</td>
	    		</tr>
	    	</table>
    	</div>
    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<c:if test="${sendMsg.state==0}">
								<input type="button" class="button" value="发&nbsp;&nbsp;送" onclick="doSend()"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="button" value="保&nbsp;&nbsp;存" onclick="doSave()"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
								<input type="button" class="button" value="关&nbsp;&nbsp;闭" onClick="doClose()" />
							</td>
						</tr>
					</table>

		</div>
		</div>
    </form>
  </body>
</html>
