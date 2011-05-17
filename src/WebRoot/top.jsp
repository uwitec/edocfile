<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/top.css">
    <style type="text/css">
    	.area1{
    		   height: 40px;
    		   background:url( "./images/topbg.gif" ) 0 0 repeat-x;
   			   display: block;
    		   clear: both;
    	}
    	#topbar {
    float: right;
    padding: 3px 2px 0 0;
    color: #fff;
}

#topbar span.sp {
    color: #a6c0e3;
    margin-left: 1px;
    margin-right: 1px;
}

#topbar span.n {
    color: #9be033;
    font-weight: bold;
}

#topbar span.msg {
    color: #fff;
    font-weight: 100;
    font-size: 10px;
}

#topbar a {
    color: #fff;
    text-decoration: none;
}

#topbar a:hover {
    color: #9be033;
    text-decoration: underline;
}
#iconbar{
 	float: left;
    padding: 3px 2px 0 0;
    color: #fff;
}
.title{
	color: #fff;
    font-weight: bold;
    font-size: 15px;
}
    </style>
 </head>
<body>
	<div class="area1">
    <div id="iconbar">
    	<table>
        	<tr>
            	<td><img src="./icon/docs-32.gif" alt="new messages" border="0" align="middle"/></td>
                <td><span class="title">企业档案管理系统</span></td>
            </tr>
        </table>
    </div>
		<div id="topbar"> 
			<span class="n">${DOCUSER.trueName }</span> <span class="sp"> | </span> 
			<a href="./jsp/person/updatePassword.jsp" target="perspective_content">我的账号</a> <span class="sp"> | </span> 
			<a href="messageAction!getReceiveMessages.action?msgType=0"  target="perspective_content">我的消息</a> <img src="./images/gotnewmsg.gif" alt="new messages" border="0" align="absmiddle"/> <span class="sp"> | </span> 
			<a href="javascript:void(0);" >帮助</a> <span class="sp"> | </span> 
			<a href="userAction!doLogout.action">退出</a> <span class="sp">  </span> 
		</div> 
	</div>
</body>
</html>