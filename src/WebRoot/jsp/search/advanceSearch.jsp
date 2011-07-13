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
		<title>高级检索</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="jsplugin/My97DatePicker/WdatePicker.js"></script>
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.contents{
				margin: 0 auto;
				width:100%;
				position:relative;
				float:left;
				height:246px;
				line-height:25px !important;
			}
		</style>
		
		<script type="text/javascript">
		
			//目录检索
			function dirSearch()(){
				var dirType = document.getElementById('dirType').value;
				var form = document.getElementById("dirSearchForm");
				if(dirType==0){
					form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
				}else if(dirType==1){
					form.action = "fileAction!getMyFilesByParentId.action?Rnd="+Math.random();
				}
				form.submit();
			}
			
		</script>
	</head>

	<body class="body1">
	<div class="area">
			<div class="pos_css">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding-left:10px;">
							<strong>您当前的位置：</strong>高级检索
						</td>
					</tr>
				</table>
			</div>
			<div class="contents" id="tabContents">
		    	<div>
		    		<form id="dirSearchForm" action="">
					<fieldset style="width:96%;margin: 0 auto;"><legend><font style="color:#1060A4;font-weight: bold;">&nbsp;目录检索&nbsp;</font></legend>
					<table width="100%" align="center" id="table2">
						<tr>
							<td width="120px" align="right">文件名称：</td>
							<td ><input id="fileName" type="text" name="fileName" size="40" value=""></td>
						</tr>
						<tr>
							<td align="right">查找目录：</td>
							<td>
								<select name="dirType">
									<option value="0">我的文件夹</option>
									<option value="1">共享文件夹</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">文档类型：</td>
							<td>
								<input name="fileType" value="0" type="checkbox" />word文档
								<input name="fileType" value="1" type="checkbox" />excel文档
								<input name="fileType" value="2" type="checkbox" />ppt文档
								<input name="fileType" value="3" type="checkbox" />pdf文档
								<input name="fileType" value="4" type="checkbox" />图片文件
								<input name="fileType" value="5" type="checkbox" />txt文本文件
								<input name="fileType" value="6" type="checkbox" />视/音频文件
							</td>
						</tr>
						<tr>
							<td align="right">时间范围：</td>
							<td>
								<input id="startTime" name="startTime" value="" class="Wdate" type="text" onclick="WdatePicker()" readonly></input>
								--
								<input id="endTime" name="endTime" value="" class="Wdate" type="text" onclick="WdatePicker()" readonly></input>
							</td>
						</tr>
						<tr>
							<td align="right"></td>
							<td>
								<input type="button" onclick="dirSearch()"  value="查询">
								<input type="reset" value="重置">
							</td>
						</tr>
					</table>
					</fieldset>	
					</form>
		      	</div> 
		      	
		      	<div>
		      		<form action="">
		      		<fieldset style="width:96%;margin: 0 auto;"><legend><font style="color:#1060A4;font-weight: bold;">&nbsp;全文检索&nbsp;</font></legend>
					<table width="100%" align="center" id="table2">
						<tr>
							<td width="120px" align="right">开始时间：</td>
							<td >
								<input type="text" value="${task.startTime }" name="task.startTime" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:160px" readonly/>
							</td>
						</tr>
						<tr>
							<td align="right">结束时间：</td>
							<td >
								<input type="text" value="${task.endTime }" name="task.endTime" id="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width:160px" readonly/>
							</td>
						</tr>
						<tr>
							<td align="right">循环次数：</td>
							<td><input id="repeatCount" value="${task.repeatCount }" name="task.repeatCount" type="text" value=""> <font color="#FF0000">(注：取值范围为-1与所有正整数，值-1表示无限循环)</font></td>
						</tr>
						<tr>
							<td align="right">间隔时间：</td>
							<td><input id="repeatInterval" value="${task.repeatInterval }" name="task.repeatInterval" type="text" value=""> <font color="#FF0000">(单位：秒， 取值范围为所有正整数)</font></td>
						</tr>
						<tr>
							<td align="right"></td>
							<td>
								<input type="submit" value="查询">
								<input type="reset" value="重置">
							</td>
						</tr>
					</table>
					</fieldset>
					</form>
		      	</div>
		    </div>
	</div>
</body>
</html>

