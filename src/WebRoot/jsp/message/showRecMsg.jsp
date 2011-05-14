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
    <title>查看收件文件</title>
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
	</script>
  </head>
  
  <body class="body1">
    <form id="save_form" action="" method="POST">
   <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>短消息管理 &gt;&gt; 查看短消息
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 330px;" class="list_div3">
	    	<table width="100%" >
	    		<tr>
	    			<td align="right" width="10%">标题：</td>
	    			<td width="70%"><input  type="text" size="50" name="message.title" value="${receiveMsg.title }" readonly/></td>
	    		</tr>
	    		<tr>
	    			<td align="right" width="10%">正文：</td>
	    			<td width="70%">
	    				<textarea name="message.content" rows="10" cols="60" readonly>${receiveMsg.content }</textarea>
	    			</td>
	    		</tr>
	    	</table>
    	</div>
    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<input type="button" class="button" value="关&nbsp;&nbsp;闭" onClick="doClose()" />
							</td>
						</tr>
					</table>

		</div>
		</div>
    </form>
  </body>
</html>
