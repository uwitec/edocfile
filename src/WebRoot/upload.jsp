<%@ page language="java" contentType="text/html; charset=utf-8"%>   
<%@ taglib prefix="s" uri="/struts-tags" %>   
<html>
    <head>
        <title>文件上传示例</title>
        <link href="<s:url value="/css/main.css"/>" rel="stylesheet"
            type="text/css" />
    </head>

    <body>

        <s:actionerror />
        <s:fielderror />
        <s:form action="fileAction!uploadFiles.action" method="post" enctype="multipart/form-data">
            <tr>
                <td colspan="2">
                    <h1>
                        文件上传示例
                    </h1>
                </td>
            </tr>

            <s:file name="docFile" label="上传的文件" />
            <s:textfield name="tt" label="备注" />
            <s:submit value="上   传"/>
        </s:form>
        
        
         <form id="save_form" action="fileAction!uploadFiles.action" method="POST" enctype="multipart/form-data">
	         <div style="width: 100%; height: 230px;" class="list_div3">
		    	<table width="100%" align="center">
		    		<tr>
		    			<td align="right" width="30%"><font color="red">*</font> 选择文件：</td>
		    			<td width="70%"><input  type="file" name="docFile"/></td>
		    		</tr>
		    	</table>
	    	</div>
	    	<div style="width: 100%; height: 5%; position: relative; float: left;"  class="list_btline">
						<table width="100%" align="center" cellspacing="0" cellpadding="2">
							<tr>
								<td align="center" height="24">
									<input type="submit" class="button" value="确&nbsp;&nbsp;认">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" class="button" value="取&nbsp;&nbsp;消" onClick="closeWin()">
								</td>
							</tr>
						</table>

				</div>
    </form>
    </body>
</html>