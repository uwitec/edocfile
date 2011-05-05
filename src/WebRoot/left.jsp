<%@ page language="java" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>">

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/left.css">

<script type="text/javascript" src="<%=basePath%>js/menu/prototype.lite.js"></script>
<script type="text/javascript" src="<%=basePath%>js/menu/moo.fx.js"></script>
<script type="text/javascript" src="<%=basePath%>js/menu/moo.fx.pack.js"></script>

<div id="container">

	<h1 class="type">
		<a href="javascript:void(0)">随访系统</a>
	</h1>
	<div class="content">
		<ul class="MM">
			<li>
				<a href="./space/space_home.jsp">首页</a>
			</li>
		</ul>
	</div>
	
	<!-- 随访任务 -->
	<h1 class="type">
		<a href="javascript:void(0)">随访任务</a>
	</h1>
	<div class="content">
		<ul class="MM">
				<li>
					<a href="followupPlan!searchFollowupPlan.action?searchParams.type=0">计划性随访</a>
				</li>
				<li>
					<a href="followupPlan!getMyPlanFollowupWork.action">计划性随访</a>
				</li>
				<li>
					<a href="followupPlan!searchFollowupPlan.action?searchParams.type=1">科室随访</a>
				</li>
				<li>
					<a href="followupPlan!getMyDeptFollowupWork.action">科室随访</a>
				</li>
				<li>
					<a href="followupPlan!searchFollowupPlan.action?searchParams.type=2&forward=showInnerFollowup">职能科随访(院内)</a>
				</li>
				 <li>
				 	<a href="followupPlan!getMyInnerFollowupWork.action">职能科随访(院内)</a>
				 </li>
				<li>
					<a href="followupPlan!searchFollowupPlan.action?searchParams.type=3">职能科随访(院外)</a>
				</li>
				 <li>
					<a href="followupPlan!getMyOccuDeptFollowupWork.action">职能科随访(院外)</a>
				</li>
		</ul>
	</div>
	
	<h1 class="type">
		<a href="javascript:void(0)">随访安排</a>
	</h1>
	<div class="content">
		<ul class="MM">
			<li>
				<a href="businessProperty!getPlanFollowupPorp.action">计划性随访安排</a>
			</li>
				<li>
					<a href="inPatient!beforeMakeDeptFollowup.action">科室随访安排</a>
				</li>
				<li>
					<a
						href="employee!beforeMakeInnerFollowup.action">职能科随访安排(院内)</a>
				</li>
				<li>
					<a href="inPatient!beforeMakeOuterFollowup.action">职能科随访安排(院外)</a>
				</li>
			 <li>
			 	<a href="followup!getMyDeptFollowups.action">科室随访安排</a>
			 </li>
			<li>
			 	<a href="followup!getMyOccuInnerFollowups.action">职能科随访安排(院内)</a>
			</li>
			<li>
			 	<a href="followup!getMyOccuOuterFollowups.action">职能科随访安排(院外)</a>
			</li>
		</ul>
	</div>

	<h1 class="type">
		<a href="javascript:void(0)">随访调查</a>
	</h1>
	<div class="content">
		<ul class="MM">
				<li>
					<a href="question!findHospQues.action">院级调查</a>
				</li>
				<li>
					<a href="question!findDeptQues.action">科级调查</a>
				</li>
		</ul>
	</div>

	<h1 class="type">
		<a href="javascript:void(0)">患者管理</a>
	</h1>
	<div class="content">
		<ul class="MM">
				<li>
					<a href="patienter!findPatienterInfo.action">患者信息</a>
				</li>
		</ul>
	</div>

	<h1 class="type">
		<a href="javascript:void(0)">满意度管理</a>
	</h1>
	<div class="content">
		<ul class="MM">
				<li>
					<a href="question!getInnerStatisQues.action">随访统计(院内)</a>
				</li>
				 <li>
					<a href="followup!getMyDeptEmplFollowup.action">随访统计(院内)</a>
				</li>
				<li>
					<a href="question!getOuterStatisQues.action">随访统计(院外)</a>
				</li>
				<li>
					<a href="followup!getMyDeptPatFollowup.action">随访统计(院外)</a>
				</li>
				
				<li>
					<a href="javascript:void(0);">综合分析</a>
				</li>
		</ul>
	</div>
	
	<h1 class="type">
		<a href="javascript:void(0)">网上医院</a>
	</h1>
	<div class="content">
	    <ul class="MM">
	      	<li><a href="schedule!getDepartsList.action?reserveforward=admin">预约挂号</a></li>
			<li><a href="diagnosis!getAllDiagSches.action">一周门诊安排</a></li>
			<li><a href="./space/reserve/save_diagnosisscheduleitem.jsp">生成门诊预约项目</a></li>
			<li><a href="diagnosisitem!getAllDiagScheItems.action">门诊预约项目</a></li>
			<li><a href="doctoroutpatient!getDoctorOutpatients.action?forward=perform">预约挂号管理</a></li>
			<li><a href="reservecharge!getAllReserveCharges.action">挂号费用管理</a></li>
			<li><a href="blacklist!getAllBlackLists.action">黑名单管理</a></li>
			<li><a href="reserveconfig!getAllConfigs.action">参数配置</a></li>
	    </ul>
	</div>

	<h1 class="type">
		<a href="javascript:void(0)">客户关怀</a>
	</h1>
	<div class="content">
		<ul class="MM">
			<li>
				<a href="bulletin!getBulletinList.action">通知</a>
			</li>
			<li>
				<a href="news!getNewsList.action">新闻</a>
			</li>
			<li>
				<a href="healthCareInfo!getHealthCareInfoList.action">健康知识</a>
			</li>
			<li>
				<a href="advisory!getAdvisoryList.action">咨询</a>
			</li>
			<li>
				<a href="advisoryCategory!getAdvisoryCategoryList.action">咨询类别设置</a>
			</li>
			<li>
				<a href="complaint!getComplaintList.action">投诉</a>
			</li>
			<li>
				<a href="complaintCategory!getComplaintCategoryList.action">投诉类别设置</a>
			</li>
		</ul>
	</div>

</div>
