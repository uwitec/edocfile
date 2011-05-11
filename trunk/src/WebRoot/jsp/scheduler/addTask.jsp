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
    <title>添加任务</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/baseinfo/user.js"></script>
	<script type="text/javascript" src="js/Validator.js"></script>
	<script type="text/javascript" src="jsplugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function closeWin(){
			this.close();
		}
		function fn_save(){
 			var r1= /^[0-9]*[1-9][0-9]*$/　　//正整数 
			var r2=/^-[0-9]*[1-9][0-9]*$/　　//负整数
			var r3=/^\d+$/   //非负整数（正整数 + 0） 
			var r4=/^-?\d+$/　　　　//整数
			var name = document.getElementById("name").value;
			var classname = document.getElementById("classname").value;
			var startTime = document.getElementById("startTime").value;
			var repeatCount = document.getElementById("repeatCount").value;
			var repeatInterval = document.getElementById("repeatInterval").value;
			if(!name){
				alert("请输入任务名称！");
				return true;
			}
			if(!classname){
				alert("请输入类名！");
				return true;
			}
			if(!startTime){
				alert("请输入开始时间");
				return true;
			}
			
			if(r4.test(repeatCount)){
				if(form1.repeatCount.value < -1){
					alert("循环次数取值范围为-1与所有正整数！");
					return true;
				}
			}else{
				alert("循环次数取值范围为-1与所有正整数！");
				return true;
			}
			if(!r3.test(repeatInterval)){
				alert("间隔时间取值范围为所有正整数！");
				return true;
			}
		
		
			var id = document.getElementById("taskId").value;
			if(!id){
				form1.action="schedulerAction!createNewTask.action";
				form1.submit();
 				return;
 			}else{
 				form1.action="schedulerAction!updateTask.action";
				form1.submit();
 				return;
 			}
 	}
		
	</script>
  </head>
  
  <body class="body1">
    <form id="form1" action="" method="POST">
    <input id="taskId" name="id" type="hidden" value="${task.id }">
     <input id="taskState" name="task.state" type="hidden" value="${task.state }">
     <input id="taskState" name="task.name" type="hidden" value="${task.name }">
   <div style="width: 100%; height: 100%; position: relative; float: left; top: 0px;">
		<div style="width: 100%; height:35px;position: relative; float: left; top: 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="font-size: 13px;" align="center" width="30" nowrap>
						<img src="images/title.gif" width="7" height="33">
					</td>
					<td>
						<strong>您当前的位置：</strong>任务调度管理 &gt;&gt; 添加任务
					</td>
				</tr>
			</table>
		</div>
		<div style="width: 100%; height: 130px;" class="list_div4">
	    	<fieldset style="width:96%;margin: 0 auto;"><legend>&nbsp;基本信息&nbsp;</legend>
			<table width="100%" align="center" id="table2">
				<tr>
					<td width="120px" align="right">任务名称：</td>
					<td ><input id="name" type="text" name="task.displayName" size="40" value="${task.displayName }"></td>
				</tr>
				<tr>
					<td align="right">全类名：</td>
					<td><input id="classname" type="text" name="task.classname" size="40" value="${task.classname }">
					<input id="type" type="hidden"  name="task.type" value="${task.type }"/>
				</tr>
				<!-- 
				<tr>
					<td align="right">调度类型：</td>
					<td >
						<input type="radio" id="type1" name="type" value="1" checked onclick="javascript:changeDiv(1);">循环执行 
						<input type="radio" id="type2" name="type" value="2" onclick="javascript:changeDiv(2);" >定期执行 
						<input type="radio" id="type3" name="type" value="3" onclick="javascript:changeDiv(3);">按周执行
						<input type="radio" id="type4" name="type" value="4" onclick="javascript:changeDiv(4);">表达式
					</td>
				</tr>
				 -->
			</table>
			</fieldset>	
   		</div>
   		<div style="width: 100%; height: 400px;" class="list_div3">
	    	<fieldset style="width:96%;margin: 0 auto;"><legend>&nbsp;循环执行参数设置&nbsp;</legend>
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
			</table>
			</fieldset>
   		</div>
    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
					<table width="100%" align="center" cellspacing="0" cellpadding="2">
						<tr>
							<td align="center" height="24">
								<input type="button" onclick="fn_save()" class="button" value="确&nbsp;&nbsp;认" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="button" value="取&nbsp;&nbsp;消" onClick="closeWin()" />
							</td>
						</tr>
					</table>

				</div>
		</div>
    </form>
  </body>
</html>
