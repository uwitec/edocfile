<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
	<base href="<%=basePath%>">
	<title>企业电子档案管理系统</title>
	<script language="javascript">

     function LinkFrame(leftSrc,rightSrc)
     {
		    parent.document.getElementById("leftFrame").src = leftSrc ;
		    parent.document.getElementById("rightFrame").src = rightSrc;
     }
	
        function toExpand()
	{
		var frm=window.parent.document.getElementById("frameset_body");
		if(frm.cols=="0,7,*"){
			frm.cols="260,7,*";
		}else{
			frm.cols="0,7,*";
		}
	}
</script>
<style type="text/css">
	body{
	    padding:0px;
		margin:0px;
		font-size:12px;
		color:#555;
	}
	.left{
	    padding:0px;
		margin:0px;
	    background:  #7b9ebd;
	}
</style>
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="left">
  <tr>
    <td>
    	<img src="images/menu_close1.jpg" id="image2" style="cursor:pointer;" onClick="toExpand();this.style.display='none';image1.style.display=''">
	    <img src="images/menu_open1.jpg"  id="image1" style="cursor:pointer;display:none" onClick="toExpand();this.style.display='none';image2.style.display=''">
	</td>
  </tr>
</table>
</body>
</html>
