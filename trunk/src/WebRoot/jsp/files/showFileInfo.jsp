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
		<title>查看文档基本信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/default.css" rel="stylesheet" type="text/css" />
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/Validator.js"></script>
		<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
			.font_red{
			    color:#B9090C;
			}
			.selectTab,.unselectTab { 
			  border:1px solid #BDDFF9; 
			  border-bottom-width: 0; 
			  width:150px; 
			  height:23px; 
			  line-height:23px; 
			  vertical-align: middle; 
			  text-align:center; 
			  background-color:#37709B; 
			  margin: 0; 
			  font-weight:bold; 
			  font-size:14px; 
			  color:#FFF; 
			  cursor: pointer; 
			  float:left; 
			 } 
			 .unselectTab { 
			  color:#37709B; 
			  background-color: white; 
			 } 
			.selectContent { 
				position:relative;
				margin: 0 auto;
			    border-top: 5px #37709b solid; 
			    padding-top: 1px; 
			    clear: both; 
			    width:100%;
			 } 
			 .unselectContent { 
			     display: none; 
			 } 
			 .contents{
				margin: 0 auto;
				width:100%;
				position:relative;
				float:left;
				height:246px;
				line-height:25px !important;
			}
			.tbar1{
				width:100%;
				float:left;
				height:25px;
				border-bottom: 1px solid #a6c5e4;
				background:url(./images/tbarbg.gif) 0 0px repeat-x;
			}
		</style>
		<script type="text/javascript">
		function getTabTitle(tab) { 
	        var childNodesList=tab.childNodes; 
	        var titleNodes=new Array(); 
	        var j=0; 
	        var i; 
	        for (i=0;i<childNodesList.length;i++) { 
	            if(childNodesList[i].nodeName=="H1") { 
	                titleNodes[j]=childNodesList[i]; 
	                j++; 
	            } 
	        } 
	        return titleNodes; 
	    } 
	    function getTabContent(tab) { 
	        var childNodesList=tab.childNodes; 
	        var tabContent=new Array(); 
	        var j=0; 
	        var i; 
	        for (i=0;i<childNodesList.length;i++) { 
	            if(childNodesList[i].nodeName=="DIV") { 
	                tabContent[j]=childNodesList[i]; 
	                j++; 
	            } 
	        } 
	        return tabContent; 
	    } 
	    function resetTab() { 
	        //var allDiv=document.getElementsByTagName("div"); 
	        //var tab=new Array(); 
	        //var j=0; 
	        //var i; 
	        //for (i=0;i<allDiv.length;i++) { 
	        //    if(allDiv[i].className=="contents") { 
	        //        tab[j]=allDiv[i]; 
	        //        j++; 
	        //    } 
	        //} 
	        //var tabTitle,tabContent; 
	        //for(i=0;i<tab.length;i++) { 
	        //    tabTitle=getTabTitle(tab[i]); 
	        //    tabTitle[0].className="selectTab"; 
	        //    tabContent=getTabContent(tab[i]); 
	        //    tabContent[0].className="selectContent"; 
	        //    for (j=1;j<tabTitle.length;j++) { 
	        //        tabTitle[j].className="unselectTab"; 
	        //        tabContent[j].className="unselectContent"; 
	        //    } 
	        //} 
	        
	        var currentTab = document.getElementById("currentTab").value;
	        if(!currentTab){
	        	currentTab = "0";
	        }
	        var allDiv=document.getElementById("tabContents"); 
	        var tabTitle=getTabTitle(allDiv); 
	        var tabContent=getTabContent(allDiv); 
	        if(currentTab == "0"){
	        	tabTitle[0].className="selectTab"; 
	        	tabContent[0].className="selectContent";
	        	
	        	tabTitle[1].className="unselectTab"; 
	        	tabContent[1].className="unselectContent"; 
	        }else if(currentTab == "1"){
	        	tabTitle[0].className="unselectTab"; 
	        	tabContent[0].className="unselectContent";
	        	
	        	tabTitle[1].className="selectTab"; 
	        	tabContent[1].className="selectContent"; 
	        }
	        
	    } 
	    function changTab(tab) { 
	        var tabTitle,tabContent,i; 
	        if(tab.className!="selectTab") { 
	            tabTitle=getTabTitle(tab.parentNode); 
	            tabContent=getTabContent(tab.parentNode); 
	            for(i=0;i<tabTitle.length;i++) { 
	                if(tabTitle[i].className=="selectTab") { 
	                    tabTitle[i].className="unselectTab"; 
	                } 
	                if(tabContent[i].className=="selectContent") { 
	                    tabContent[i].className="unselectContent"; 
	                } 
	            } 
	        	tab.className="selectTab"; 
	        	for(i=0;i<tabTitle.length;i++) { 
	            	if(tabTitle[i].className=="selectTab") { 
	                	tabContent[i].className="selectContent"; 
	                	break; 
	            	} 
	        	} 
	        } 
	    } 
    function closeWin(){
    	window.close();
    }
    
    function addFileVersion(){
    		var params = [];
    		var sourceFileId = document.getElementById('sourceFileId').value;
			params[0] = sourceFileId;
			var sourceFileName = document.getElementById('sourceFileName').value;
			params[1] = sourceFileName;
			var returnValue = showModalDialog("jsp/files/addFileVersion.jsp", params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
			
			var form = document.getElementById("queryForm1");
			form.action="fileAction!getEdocFileVersions.action";
			form.submit();
    }
    
    function init(){
    	resetTab();
    	var params = window.dialogArguments;	//参数为上一级文件夹的Id
    	document.getElementById('sourceFileName').value=params[0];
    }
    function showBaseFileInfo(){
    	var form = document.getElementById("queryForm1");
    	form.action="fileAction!getFileInfo.action";
    	form.submit();
    }
    
    function showVersion(){
    	var form = document.getElementById("queryForm1");
    	form.action="fileVersionAction!getFileVersions.action";
    	form.submit();
    }
    
	    //翻页操作
	function openPage(currentPage){
		document.getElementById("currentPage_param").value=currentPage;
		var form = document.getElementById("queryForm1");
		form.action="fileVersionAction!getFileVersions.action";
		form.submit();
	}
	
	//文档预览
	function previewFile(id,version){
		window.open("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(),"","resizable=yes,status=no,toolbar=no,menubar=no,location=no");
		//showModalDialog("fileAction!beforePreviewFile.action?sourceFileId="+id+"&version="+version+"&Rnd="+Math.random(), "", "dialogWidth:1000px; dialogHeight:600px;help:no;scroll:no;status:no");
	}
	</script>
	</head>

	<body class="body2" onload="init()">
	<form id="queryForm1" action="" method="post">
			<input type="hidden" name="currentPage" id="currentPage_param" value="${fileVersions.currentPage }" />
			<input type="hidden" name="sourceFileId" id="sourceFileId" value="${sourceFileId }" />
			<input id="currentTab" type="hidden" value="${currentTab }">
			<input id="sourceFileName" type="hidden">
	</form>
	<div class="area">
			<div style="width: 100%; height:6%;position: relative; float: left; top: 0px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="font-size: 13px;" align="center" width="30" nowrap>
							<img src="images/title.gif" width="7" height="32">
						</td>
						<td>
							<strong>您当前的位置：</strong>系统管理 &gt;&gt; 查看版本信息
						</td>
					</tr>
				</table>
			</div>
			
			<div class="contents" id="tabContents">
				<h1 onclick="showBaseFileInfo()">基本信息</h1> 
		    	<h1 onclick="showVersion()">版本信息</h1> 
		    	<div>
			    	<table cellspacing="0" class="from_table" style="overflow-x:scroll;">
						  <tr>
						    <td width="20%" align="right">文件名称：</td>
						    <td width="80%">${edocFile.fileName }</td>
						  </tr>
						  <tr>
						    <td align="right">文件大小：</td>
						    <td>${edocFile.fileSize }</td>
						  </tr>
						  <tr>
						    <td align="right">文件类型：</td>
						    <td>${edocFile.fileType }</td>
						  </tr>
						  <tr>
						    <td align="right">修改日期：</td>
						    <td>${edocFile.createTime }</td>
						  </tr>
						  <tr>
						    <td align="right">当前版本：</td>
						    <td>${edocFile.currentVersion }</td>
						  </tr>
						  <tr>
						    <td>&nbsp;</td>
						    <td>&nbsp;</td>
						  </tr>
						  <tr>
						    <td>&nbsp;</td>
						    <td>&nbsp;</td>
						  </tr>
						</table>

		      </div> 
		    <div>
		    	<div class="tbar1">
					<ul id="nav">
						<li>
							<a href="javascript:void(0);" onclick="addFileVersion()"><img src="icon/add.png"/>新增版本</a>
						</li>
						<!-- 
						<li>
							<a href="javascript:void(0);" onclick="deleteMore()"><img src="icon/delete.png"/>&nbsp;删除</a>
						</li>
						 -->
					</ul>
				</div>
				<div class="content">
				<table cellspacing="0" class="list_table2" style="overflow-x:scroll;">
						<tr bgcolor="#F2F4F6">
							<th width="1%"><input id="selectAll" type="checkbox" onclick="selectAllCheckbox()"></input></th>
							<th>
								名称
							</th>
							<th>
								大小
							</th>
							<th>
								当前版本
							</th>
							<th>
								更改说明
							</th>
							<th>
								修改人
							</th>
							<th>
								修改时间
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach var="v" items="${fileVersions.result}">
							<tr>
								<td align="center">
									<input name="checkItem" value="${v.id }" type="checkbox"></input>
								</td>
								<td>
									<img src="${v.icon }"/>&nbsp;${v.fileName }
								</td>
								<td align="right">
									${v.fileSize }&nbsp;KB
								</td>
								<td align="right">
									${v.version }
								</td>
								<td align="right">
									${v.desc }
								</td>
								<td align="right">
									${v.updateUserName }
								</td>
								<td>
									${v.updateTime }
								</td>
								<td align="center">
									&nbsp;&nbsp;<a href="javascript:void(0);" onclick="previewFile('${v.edocFileId }','${v.version }')" >预览</a>
									<!-- &nbsp;&nbsp;<a href="javascript:void(0);" >共享</a> -->
									<!-- &nbsp;&nbsp;<a href="javascript:void(0);">删除</a> -->
								</td>
							</tr>
						</c:forEach>
				</table>
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
              		<tr> 
                		<td align="right" nowrap>共 
                  		<span class="font_red">${fileVersions.totalRows }</span> 条数据 当前第 <span class="font_red">${fileVersions.currentPage }</span> 
                  		页 共 <span class="font_red">${fileVersions.totalPages }</span> 页 
                  		<img src="images/first.gif" width="18" height="18" onclick="openPage(1)" title="首页"> 
                  		<img src="images/previous.gif" width="18" height="18" onclick="openPage(${fileVersions.prePage})" title="上一页">
                  		<img src="images/next.gif" width="18" height="18" onclick="openPage(${fileVersions.nextPage})" title="下一页"> 
                  		<img src="images/last.gif" width="18" height="18" onclick="openPage(${fileVersions.totalPages})" title="末页"> </td>
              		</tr>
            	</table>
			</div>
			</div>
	</div>
</body>
</html>

