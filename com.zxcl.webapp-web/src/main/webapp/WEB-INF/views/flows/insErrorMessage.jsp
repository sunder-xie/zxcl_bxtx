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
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>

<html>
<head>
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
</head>
<body>
	<!--横屏显示和loading start-->
	<div class="cover">
		<div class="iphone">
			<img src="${ctx}images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
<!-- 	<div class="loading"> -->
<!-- 		<div class="load-container load6"> -->
<!-- 			<div class="loader">Loading...</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!--横屏显示和loading end-->

	<!--主体内容 start-->
	<div class="wrapper mb15">
		<div class="wrap_top" style="background: #DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back" id="returnBack"><img src="${ctx}images/back.png"></a>
				<a class="logo bjjg">报价失败</a>
				<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
		<div class="chunk mb5">
			<c:forEach items="${newList }" var="obj">
				<div class="" style="white-space:normal;word-wrap:break-word;word-break:break-all;">
					<h2>${obj.insName }</h2>
					<ul class="mb5">
						<li class="ptb2">${obj.message }</li>
					</ul>
					<hr/>
				</div>
			</c:forEach>
		</div>		
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>


<!--<script type="text/javascript" src="js/zepto.min.js"></script>-->
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script>
changeMenuTab(1);
$(function(){
	$('#returnBack').on('click',function(){
		window.location.href='${ctx}toquota.do?inquiryId=${inquiryId}&useType=Q';
	});
});
</script>
</body>
</html>