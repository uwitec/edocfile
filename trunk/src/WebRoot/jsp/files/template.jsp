<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
<title>模板字段设计</title>
<link rel="stylesheet" type="text/css" href="css/default.css" />
</head>
<body>
<form name="form1" action="" method="post" >
<div style="width:100%;height:100%;position:relative;float:left;top:0px;">
<div style="width:100%;height:5%;position:relative;float:left;top:0px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	     <td style="font-size: 13px;" align="center" width="30" nowrap><img src="images/title.gif" width="7" height="33"></td>
     	<td><strong>您当前的位置：</strong>系统管理  &gt;&gt;  模板管理  &gt;&gt;  模板字段设计 </td>
	  </tr>
</table>
</div>
<div style="width:100%;height:85%;position:relative;overflow:auto;float:left;">
	<table width="100%" border="0" cellpadding="3" cellspacing="0" bgcolor="ffffff" class="tableborder" style="overflow-x:scroll;">		
		<tr bgcolor="#F2F4F6"> 
		<td class="toptd1"><input name="chkTitle" type="checkbox" onClick="selectCHK(this,'chkData')" title="选中/取消选中" /></td>
		<td class="toptd">字段名称</td>
		<td class="toptd">字段类型</td>
		<td class="toptd">著录项名称</td>
		<!--  <td class="toptd">著录项标题(英文)</td>-->
		<td class="toptd">列宽</td>
		<td class="toptd">是否可见</td>
		<td class="toptd">字段长度</td>
		<td class="toptd">默认值</td>
		<!-- <td class="toptd">格式化</td> -->
		<td class="toptd">属性</td>
		<td class="toptd">操作</td>
		</tr>
</table>
</div>
<div style="width:100%;height:5%;position:relative;float:left;">
	<table width="100%" align="center" cellspacing="0" cellpadding="2" class="list_btline">
  <tr>
    <td align="center" height="24"  class="f_size selbar_bt barspace2">
      <input type="button" name="" class="button" value="确认" onClick="onDelete('chkData')" >
   </td>
    <td align="right" class="selbar_bt">　</td>
  </tr>
  <tr class="toolbg">
    <td width="99%" align="left" nowrap class="barspace toolbgline">　</td>
    
</tr>
</table>
	
</div>
</div>
</form>
</body>
</html>