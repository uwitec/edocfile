<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    <title>登 录</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css">
	<style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-color: #1D3647;
		}
		.title{
			color: #1D3647;
		    font-weight: bold;
		    font-size: 15px;
		    line-height:40px;
		}
	</style>
	<script type="text/javascript">
		function do_login(){
			var username=document.getElementById('username').value;
			if(username==""){
				var error_msg=document.getElementById('error_msg');
				error_msg.innerHTML="<font color='red'>用户名不能为空!</font>";
				return;
			}
			
			var password=document.getElementById('password').value;
			if(password==""){
				var error_msg=document.getElementById('error_msg');
				error_msg.innerHTML="<font color='red'>密码不能为空!</font>";
				return;
			}
			var form = document.getElementById("loginForm");
			form.action = "userAction!doLogin.action";
			form.submit();
			//var error_msg=document.getElementById('error_msg');
			//error_msg.innerHTML="&nbsp;";
			//window.location="index.jsp"
		}
		
		function correctPNG()
		{
		    var arVersion = navigator.appVersion.split("MSIE")
		    var version = parseFloat(arVersion[1])
		    if ((version >= 5.5) && (document.body.filters)) 
		    {
		       for(var j=0; j<document.images.length; j++)
		       {
		          var img = document.images[j]
		          var imgName = img.src.toUpperCase()
		          if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
		          {
		             var imgID = (img.id) ? "id='" + img.id + "' " : ""
		             var imgClass = (img.className) ? "class='" + img.className + "' " : ""
		             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
		             var imgStyle = "display:inline-block;" + img.style.cssText 
		             if (img.align == "left") imgStyle = "float:left;" + imgStyle
		             if (img.align == "right") imgStyle = "float:right;" + imgStyle
		             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
		             var strNewHTML = "<span " + imgID + imgClass + imgTitle
		             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
		             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
		             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>" 
		             img.outerHTML = strNewHTML
		             j = j-1
		          }
		       }
		    }    
		}
	</script>
  </head>
  
  <body onLoad="correctPNG()">
  <form id="loginForm" action="">
  <table width="100%" height="166" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="42" valign="top"><table width="100%" height="42" border="0" cellpadding="0" cellspacing="0" class="login_top_bg">
      <tr>
        <td width="1%" height="21">&nbsp;</td>
        <td height="42">&nbsp;</td>
        <td width="17%">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
  <td valign="top"><table width="100%" height="632" border="0" cellpadding="0" cellspacing="0" class="login_bg">
      <tr>
        <td width="49%" align="right"><table width="91%" height="632" border="0" cellpadding="0" cellspacing="0" class="login_bg2">
            <tr>
              <td height="238" valign="top"><table width="89%" height="527" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="249" colspan="3">&nbsp;</td>
                </tr>
                 <tr>
                  <td width="35%" height="40" align="left" valign="top">&nbsp;</td>
                  <td width="10%" align="left" valign="top"><img src="./icon/docs-32.gif" alt="new messages" border="0" align="middle"/></td>
                  <td width="55%" align="left" valign="top"><span class="title">企业档案管理系统</span></td>
                 </tr>
                <tr>
                  <td height="198" colspan="3" align="right" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35%">&nbsp;</td>
                      <td height="25" colspan="2" class="left_txt"><p>1.减少企业知识文档流失率</p></td>
                       </tr>
                    <tr>
                      <td>&nbsp;</td>
                       <td height="25" colspan="2" class="left_txt"><p>2.提供稳定获取效率</p></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td height="25" colspan="2" class="left_txt"><p>3.提高企业员工工作效率</p></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td height="25" colspan="2" class="left_txt"><p>4.减低企业管理成本</p></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td width="30%" height="40"></td>
                      <td width="35%"></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
             </table></td>
        <td width="1%" >&nbsp;</td>
        <td width="50%" valign="bottom"><table width="100%" height="59" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%">&nbsp;</td>
              <td width="96%" height="38"><span class="login_txt_bt">登录企业档案管理系统</span></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="21"><table cellSpacing="0" cellPadding="0" width="100%" border="0" id="table211" height="328">
                  <tr>
                    <td height="164" colspan="2" align="center">
                        <table cellSpacing="0" cellPadding="0" width="100%" border="0" height="143" id="table212">
                          <tr>
                            <td width="13%" height="38" class="top_hui_text"><span class="login_txt">用户名：&nbsp;&nbsp; </span></td>
                            <td height="38" colspan="2" class="top_hui_text"><input  id="username" name="j_username" class="editbox4" value="" size="20">                            </td>
                          </tr>
                          <tr>
                            <td width="13%" height="38" class="top_hui_text"><span class="login_txt"> 密 码： &nbsp;&nbsp; </span></td>
                            <td height="35" colspan="2" class="top_hui_text"><input id="password" name="j_password" class="editbox4" type="password" size="20">
                              <img src="images/luck.gif" width="19" height="18"> </td>
                          </tr>
                          <tr>
                            <td height="35" >&nbsp;</td>
                            <td width="20%" height="35" ><input name="Submit" type="button" onClick="do_login()" class="button" id="Submit" value="登 陆"> </td>
                            <td width="67%" class="top_hui_text"><input name="cs" type="reset" class="button" id="cs" value="取 消"></td>
                          </tr>
                        </table>
                        <br>
                    </td>
                  </tr>
                  <tr>
                    <td width="433" height="164" align="right" valign="bottom"><img src="images/login-wel.gif" width="242" height="138"></td>
                    <td width="57" align="right" valign="bottom">&nbsp;</td>
                  </tr>
                  
              </table></td>
            </tr>
          </table>
          </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="20"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="login-buttom-bg">
      <tr>
        <td align="center"><span class="login-buttom-txt">企业文档管理系统v1.0  &copy; 2011</span></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
  </body>
</html>
