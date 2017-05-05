<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
 	<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	   System.out.println(request.getRequestURI());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
		 System.out.println(request.getRequestURI());
	}
%> 

<html>
<head>
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
<!--横屏显示和loading start-->
<div class="cover">
        <div class="iphone" ><img src="${ctx}images/iphone.png"/></div>
        <p>为了更好的用户体验，请将屏幕竖向浏览！</p>
    </div>
<!-- <div class="loading"> -->
<!-- 	<div class="load-container load6"> -->
<!-- 	  <div class="loader">Loading...</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!--横屏显示和loading end-->

	<!--主体内容 start-->
	<div class="wrapper mb15">
		<div class="wrap_top">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a>
				<a class="logo"><img src="${ctx}images/logo.png"></a>
				<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
		<div class="chunk mb5">
			<div class="con mauto">
				<h2 class="mb5">详细列表</h2>
				<input type="hidden" id="queryStage" value="${order.queryStage }">
				<table class="orderList" cellpadding="0" cellspacing="0">
					<tr class="bg33c fcfff fb">
						<td width="10%">车牌</td>
						<td width="8%">保险公司</td>
						<td width="16%">保费</td>
						<td width="15%">姓名</td>
						<td width="13%">状态</td>
						<td width="13%">操作</td>
					</tr>
					 <c:forEach items="${orderList}" var="order" varStatus="i">			
				 		<c:if test="${order.queryStage eq '3' and not empty order.quota.quotaId}" >			
							<tr class="bb1e4 quotaFailInfo" id="tabletr${i.index}">
								<td>${order.inquiry.plateNo}</td>
								<td>${order.insurance.insId}</td>
								<td>￥${order.quota.totalAmount}</td> 
								<td>${order.inquiry.ownerName}</td>
								<td class="bbb"><span>失败</span></td>
<%-- 								<td><a class="smallBtn" href="comPrice.do?code=${order.insurance.insId }&ownerName=${order.inquiry.ownerName }&inquiryId=${order.inquiry.inquiryId}">核保</a></td> --%>
							</tr>
							<c:forEach items="${quotas}" var="quota">
								<c:if test="${quota.quotaId eq order.quota.quotaId}">
									<tr id="${quota.quotaId}" class="bb1e4 qoutaInfo" style="display: none;">
										<td></td>
										<td>${quota.insurance.insId }</td>
										<td>￥${quota.totalAmount }</td>
										<td></td>
										<td></td>
<%-- 										<td><a href="${ctx}premium.do?insId=${quota.insurance.insId }&quotaId=${quota.quotaId }&ownerName=${order.inquiry.ownerName}&orderId=${order.orderId}" class="smallBtn bbb">核保</a></td>--%>
											<td><a href="${ctx}insuranceType.do?inquiryId=${order.inquiry.inquiryId}&useType=1" class="smallBtn bbb">报价</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
				</table>
				<br/>
			</div>
		</div>
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>
	
	<div class="wrap_result dbn" id="select_result">
		<div class="result_box">
			<div class="showBtn">
				<div class="result_top">查询结果</div>
				<p class="result_con" id="resultInfo">订单状态<br/>请稍后查询保单</p>
				<div class="result_bottom">
					<a class="btn_l result_close">取消</a>
					<a class="btn_r result_close" id="result_show">查看</a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/rote.jsp"></jsp:include>
<%--<script type="text/javascript" src="js/zepto.min.js"></script>--%>
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script type="text/javascript">
changeMenuTab(2);
$(document.body).ready(function(){
	$(".quotaFailInfo").on("click",function(){
		$(".qoutaInfo").attr("style","display:none");
		removeStyle($(this));
	});
	
	var removeStyle=function(me){
		var next=me.next(".qoutaInfo");
		if(next.attr("style")){
			$(next).attr("style","");
			removeStyle(next);
		}else{
			return;
		}
	}
});

</script>
</body>
</html>