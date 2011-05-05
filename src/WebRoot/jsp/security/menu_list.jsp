<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<base href="<%=basePath%>">
		<title>菜单管理</title>
		<link href="css/comm.css" rel="stylesheet" type="text/css" />
		<link href="css/tbar.css" rel="stylesheet" type="text/css" />
		<link href="tree-table/stylesheets/master.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/javascripts/jquery.js"></script>
		<script type="text/javascript" src="tree-table/javascripts/jquery.ui.js"></script>
		<script type="text/javascript" src="js/security/permission.js"></script>

		<!-- BEGIN Plugin Code -->

		<link href="tree-table/src/stylesheets/jquery.treeTable.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="tree-table/src/javascripts/jquery.treeTable.min.js"></script>
		
		<script type="text/javascript" src="js/menu.manager.js"></script>
		<script type="text/javascript">
		  $(document).ready(function() {
		    $("#dnd-example").treeTable();
		    
		    // Drag & Drop Example Code
		    $("#dnd-example .file, #dnd-example .folder").draggable({
		      helper: "clone",
		      opacity: .75,
		      refreshPositions: true, // Performance?
		      revert: "invalid",
		      revertDuration: 300,
		      scroll: true
		    });
		    
		    $("#dnd-example .folder").each(function() {
		      $($(this).parents("tr")[0]).droppable({
		        accept: ".file, .folder",
		        drop: function(e, ui) { 
		          $($(ui.draggable).parents("tr")[0]).appendBranchTo(this);
		        },
		        hoverClass: "accept",
		        over: function(e, ui) {
		          if(this.id != ui.draggable.parents("tr.parent")[0].id && !$(this).is(".expanded")) {
		            $(this).expand();
		          }
		        }
		      });
		    });
		    
		    // Make visible that a row is clicked
		    $("table#dnd-example tbody tr").mousedown(function() {
		      $("tr.selected").removeClass("selected"); // Deselect currently selected rows
		      $(this).addClass("selected");
		    });
		
		    // Make sure row is selected when span is clicked
		    $("table#dnd-example tbody tr span").mousedown(function() {
		      $($(this).parents("tr")[0]).trigger("mousedown");
		    });
		  });
  
  	</script>

		<script type="text/javascript">
		function menuFix() {
			var sfEls = document.getElementById("nav").getElementsByTagName("li");
			for (var i=0; i<sfEls.length; i++) {
				sfEls[i].onmouseover=function() {
				this.className+=(this.className.length>0? " ": "") + "sfhover";
			}
			sfEls[i].onMouseDown=function() {
				this.className+=(this.className.length>0? " ": "") + "sfhover";
			}
			sfEls[i].onMouseUp=function() {
				this.className+=(this.className.length>0? " ": "") + "sfhover";
			}
			sfEls[i].onmouseout=function() {
				this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
			}
			}
		}
		window.onload=menuFix;
		</script>
			<style type="text/css">
			IMG {
				border-width: 0px;
				margin-bottom:-3px;
			}
		</style>
	</head>
	<body>
		<div class="area">
			<div class="tbar">
				<ul id="nav">
					<li>
						<a href="javascript:void(0);" onclick="add_permission()"><img src="icon/add.png"/>&nbsp;新建</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="edit_permission()"><img src="icon/edit.png"/>&nbsp;编辑</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="delete_permission()"><img src="icon/delete.png"/>&nbsp;删除</a>
					</li>
				</ul>
			</div>
			<div class="content">
				<table class="example" id="dnd-example">
					<thead>
						<tr>
							<th>
								菜单名称
							</th>
							<th>
								路径
							</th>
							<th>
								创建日前
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="menu" items="${menuList}">
						<c:if test="${menu.parentMenuId=='-1'}">
							<tr id='node-${menu.id}'>
									<td>
										<span class='folder'>${menu.name }</span>
									</td>
									<td>
										${menu.id }
									</td>
									<td>
										480.95 KB
									</td>
							</tr>
						</c:if>
						<c:if test="${menu.parentMenuId!='-1'}">
							<tr id="node-${menu.id}" class="child-of-node-${menu.parentMenuId }">
									<td>
										<span class='folder'>${menu.name }</span>
									</td>
									<td>
										${menu.id }
									</td>
									<td>
										480.95 KB
									</td>
							</tr>
						</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="bbar">
			</div>
		</div>
	</body>
</html>