<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.edoc.utils.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<% 
	String fileName = (String)request.getAttribute("fileName");
	
	String filePath = basePath + "temp/" + fileName+"_#_DISPLAYFILENAME";
	Md5Double des = new Md5Double();    
    filePath = des.encrypt(filePath);
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看原文</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript">
		function ShowActiveX(){
			try{
				document.getElementById("afrmxCommonFileView1").SetTransportMode(2);
				document.getElementById("afrmxCommonFileView1").SetEncrypt(1);
				document.getElementById("afrmxCommonFileView1").SetCanPrint(0);
				document.getElementById("afrmxCommonFileView1").SetCanDownload(1);
				document.getElementById("afrmxCommonFileView1").SetDisablePrtScrn(1);
				document.getElementById("afrmxCommonFileView1").SetTransportPath("<%=filePath %>");
				document.getElementById("afrmxCommonFileView1").ShowFile();		
			}catch(err){
				alert('系统检测到您电脑中未安装系统插件，请下载安装');
				document.form1.submit();
			}
		}
	</script>
  </head>
  
  <body  onload = "javascript: ShowActiveX();">
  	<form name="form1"  target="_blank"  action="sfile/DocView.exe" method="post">
	</form>
   	<OBJECT id="afrmxCommonFileView1"
		classid="clsid:5858AFC0-2EF5-45AA-B891-CE604CF228B7"
		type="application/x-oleobject"
		align="baseline" border="0"
		codebase="<%=request.getContextPath()%>/back/uploadfile/PDEReader.rar"
		WIDTH="100%" height="660">      
    </OBJECT>
  </body>
</html>
