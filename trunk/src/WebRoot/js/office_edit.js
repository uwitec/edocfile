//文档在线预览、编辑
//陈超 2011-05-26



//保存文档信息
function save_office(){
	
  	var edocfile = document.getElementById('edocfile-office');
  	try{
        //edocfile.Save();
        
        
        //添加 更改说明
        var versionDesc = "";
        var params = [];
		params[0] = document.getElementById('fileName').value;
		var returnValue = showModalDialog("jsp/files/addVersionDesc.jsp?Rnd="+Math.random(), params, "dialogWidth:600px; dialogHeight:300px; status:0; help:0");
        if(returnValue){
        	if(returnValue[0]){
        		versionDesc = returnValue[0];
        	}
        }
        edocfile.HttpInit();
		edocfile.HttpAddPostCurrFile("fileObj","");//上传当前文件 
		var sourceFileId = document.getElementById('sourceFileId').value;
		edocfile.HttpAddPostString("sourceFileId",sourceFileId);
		if(versionDesc!=null && versionDesc!=""){
			edocfile.HttpAddPostString("versionDesc",versionDesc);
		}
		returnValue = edocfile.HttpPost("./fileVersionAction!addFileVersionFromOnline.action?Rnd="+Math.random());
		
		if(returnValue=="true"){
			edocfile.Save();
			if(confirm('保存成功!是否退出当前编辑窗口？')){
				window.close();
				opener.reloadPage();
			}
		}else{
			alert('保存失败!');
		}
    }catch(e){
    	alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
    }
}


//退出该窗口
//如果做过修改并且未保存的话要提示用户是否保存修改信息
//如果做过修改则增加一条版本记录(让用户填写修改备注之类的表单)
function exit_office(){
	//var edocfile = document.getElementById('edocfile-office');
	//var isSave = edocfile.IsSaved();
	
	//alert(edocfile.IsModify);
	
	window.close();
	opener.reloadPage();
	
}



//编辑文档
//如果该用户没有权限编辑文档的话则弹出申请编辑权限的对话框
//编辑过程中要记录修订信息
function edit_office(){
	
}


//显示文档的修订信息
function show_office_editinfo(){
	 var edocfile = document.getElementById('edocfile-office');
     edocfile.ShowRevisions(1);
}



//隐藏文档的修订信息
function hidden_office_editinfo(){
	var edocfile = document.getElementById('edocfile-office');
    edocfile.ShowRevisions(0);
}


function exit(){

	window.close();
	opener.reloadPage();
}

//检查该用户是否拥有编辑权限,如果没有的话则弹出申请编辑权限的窗口,相反则进入编辑页面
function before_edit_office(sourceFileId,userId,fileSuffix){
	if(fileSuffix){
		if(fileSuffix!="doc" && fileSuffix!="xls" && fileSuffix!="ppt" && fileSuffix!="docx" && fileSuffix!="xlsx" && fileSuffix!="pptx"){
			alert("系统目前不支持"+fileSuffix+"文档的在线编辑操作!");
			return;
		}
		
		$.ajax({
		    url:"visitUserAction!checkPermission.action?currentUserId="+userId+"&sourceFileId="+sourceFileId+"&perType=edit&Rnd="+Math.random(), 
		    type:'post',
		    error: function(){
		    	return false;
		    },
		    success: function(json){
		    	if(json!=null && json!=""){
		    		if(json=="true"){
		    			var form = document.getElementById("previewFileForm");
		    			form.action = "fileAction!beforePreviewFile.action?forward=editFile&Rnd="+Math.random();
		    			form.submit();
		    		}else{
		    			alert('您没有权限对该文档执行编辑操作,需要申请编辑权限!');
		    		}
		    	}else{
		    		alert('编辑操作失败!');
		    	}
		   }
	   	});
	
	}
}


//检查该用户是否拥有编辑权限,如果没有的话则弹出申请编辑权限的窗口,相反则进入编辑页面
function before_edit_office_open_newwin(sourceFileId,userId,fileSuffix,version){
	if(fileSuffix){
		if(fileSuffix!="doc" && fileSuffix!="xls" && fileSuffix!="ppt" && fileSuffix!="docx" && fileSuffix!="xlsx" && fileSuffix!="pptx"){
			alert("系统目前不支持"+fileSuffix+"文档的在线编辑操作!");
			return;
		}
		
		$.ajax({
		    url:"visitUserAction!checkPermission.action?currentUserId="+userId+"&sourceFileId="+sourceFileId+"&perType=edit&Rnd="+Math.random(), 
		    type:'post',
		    error: function(){
		    	return false;
		    },
		    success: function(json){
		    	if(json!=null && json!=""){
		    		if(json=="true"){
		    			window.open("fileAction!beforePreviewFile.action?forward=editFile&sourceFileId="+sourceFileId+"&version="+version+"&Rnd="+Math.random(),"","resizable=yes,status=no,toolbar=no,menubar=no,location=no");
		    		}else{
		    			alert('您没有权限对该文档执行编辑操作,需要申请编辑权限!');
		    		}
		    	}else{
		    		alert('编辑操作失败!');
		    	}
		   }
	   	});
	
	}
}