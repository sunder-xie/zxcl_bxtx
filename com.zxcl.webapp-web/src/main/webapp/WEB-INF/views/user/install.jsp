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
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" content="no-cach">
<meta name="robots" content="all">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="css/reset.min.css">
<link rel="stylesheet" type="text/css" href="css/iconfont.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="js/jquery.1.7.2.min.js" ></script>
</head>
<body>
<!--横屏显示和loading start-->
<div class="cover">
        <div class="iphone" ><img src="images/iphone.png"/></div>
        <p>为了更好的用户体验，请将屏幕竖向浏览！</p>
    </div>
<!-- <div class="loading"> -->
<!-- 	<div class="load-container load6"> -->
<!-- 	  <div class="loader">Loading...</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!--横屏显示和loading end-->

	<!--主体内容 start-->
	<div class="wrap_top">
		<div class="con mauto clearfix">
			<a class="back"><img src="images/back.png"></a>
			<a class="logo"><img src="images/logo.png"></a>
			<a class="tel"  href="tel:${sessionScope.tel }"><img src="images/phone.png"></a>	
		</div>
	</div>
	<div class="chunk mb5">
		<div class="con mauto">
			<h2 class="mb5">个人资料及常见问题</h2>
			<ul class="list">
				<li><a class="arrow_r" href="personal.do">个人信息修改</a></li>
				<li><a class="arrow_r" href="flow.htm">系统流程</a></li>
				<li><a class="arrow_r" href="question.do">常见问题</a></li>
			</ul>
		</div>
	</div>
	<!--主体内容 end-->

<!--<script type="text/javascript" src="js/zepto.min.js"></script>-->
<script type="text/javascript" src="js/jquery-html5Validate.js"></script>
<!-- <script type="text/javascript" src="js/prettify.js"></script> -->
<script type="text/javascript" src="js/main.js"></script>
<script> 
changeMenuTab(3);
	$("form").html5Validate(function() {
		//alert("全部通过！");
		//this.submit();	
	}, {
		// novalidate: false,
		// labelDrive: false
		submitEnabled:true
	});
</script>
</body>
</html>