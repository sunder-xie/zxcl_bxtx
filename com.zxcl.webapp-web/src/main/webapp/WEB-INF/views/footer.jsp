<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="menu">
		<div><a class="tab" href="${ctx}index.do?nocheck=1">报价</a></div>
		<div><a class="tab" href="javascript:void(0);">查询</a>
			<ul class="subM">
<%-- 					<li><a href="${ctx}list1.do">核保</a></li> --%>
				<li><a href="${ctx}list2.do">支付</a></li>
				<li><a href="${ctx}list3.do">保单</a></li>
<%-- 					<li><a href="${ctx}list4.do">核保失败</a></li> --%>
<%-- 					<li><a href="${ctx}informationTempQuery.do">暂存报价信息</a></li> --%>
				<li><a href="${ctx}infoQuery.do">综合查询</a></li>
			</ul>
		</div>
		<div><a class="tab bdn"  href="javascript:void(0);">发现</a>
			<ul class="subM">
				<li><a href="${ctx}personal.do">我的信息</a></li>
				<li><a href="${ctx}cjwt.do">常见问题</a></li>
				<li><a href="${ctx}myTeam.do">我的团队</a></li>
				<li><a href="${ctx}used.do">使用指南</a></li>
				<!--<li><a href="${ctx}#.do">支付指南</a></li>-->
				<li><a href="${ctx}performance.do">业绩报表</a></li>
			</ul>
		</div>
	</div>
