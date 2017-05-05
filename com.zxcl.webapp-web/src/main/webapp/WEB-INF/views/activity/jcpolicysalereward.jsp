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

	String inquiryId = request.getParameter("inquiryId");
	if (inquiryId == "") {
		request.setAttribute("inquiryId", false);
	} else {
		request.setAttribute("inquiryId", true);
	}
%>
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon"
	href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/jquery-ui.css"/> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/jquery.cookie.js"></script>
<style>
html {
	overflow-x: hidden;
}

.pin-cx {
	width: 50%;
	text-transform: uppercase;
}

.bjmp .bjmp_ischeck {
	padding: 2% 0 2% 9.6%;
}

.mauto {
	margin: 0 2%;
}

.menu .tab {
	padding: 5% 0;
	margin: 0 0 0 0;
}

input[type='date'] {
	display: inline-block;
	width: 52%;
	border: 1px solid #2BA5DF;
}

#js_info_list input[type='checkBox'] {
	border-radius: 50%;
}

.chunk {
	padding: 3% 0 9% 0% !important;
	background-color: #F2F2F2 !important;
}

select {
	height: 30px;
	background-color: white;
	margin-left: -1.2%;
}

h2 {
	font-size: 16px;
	color: #fe5a00;
}

@media screen and (min-width:760px) {
	/* 	input[type="checkbox"], input[type="radio"] { */
	/* 		width: 40px; */
	/* 		height: 40px; */
	/* 	} */
	.hei-30 {
		height: 30px !important;
		line-height: 30px !important;
	}
}
</style>
</head>
<body>
	<!--横屏显示和loading start-->
	<div class="cover">
		<div class="iphone">
			<img src="${ctx}images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
	<div class="">
		<div class="wrap_top" style="background: #DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a> <a
					class="logo bjjg">嘉诚活动</a> <a class="tel"
					href="tel:${sessionScope.tel }"><img
					src="${ctx}images/phone.png"></a>
			</div>
		</div>
	</div>
	<div class="swiper-container">
		<div class="swiper-wrapper">
			<div class="swiper-slide">
				<img src="${ctx}activity/5_1jcpolicysale/coujiang_05.png">
			</div>
		</div>
		<div id="immediately" style="position:absolute;bottom:15%;left:28%; ">
			<div  onclick="now()">
				<img src="${ctx}activity/5_1jcpolicysale/coujiang_24.png"
					 />
			</div>
		</div>
		<div id="immediately1" style="position:absolute;bottom:30%;left:35%; ">
			<div style="color:white;font-size:7vw;">嘉诚代理</div>
		</div>
		<div id="immediately2" style="position:absolute;bottom:25%;left:43%; ">
			<div style="color:white;font-size:7vw;">
				<a style="color:white;font-size:3vw;" href="${ctx }jumpToJCPlolicySaleCopyWritePage.do">查看活动规则</a>
			</div>
		</div>
	</div>
	<div id="all"
		style="display:none;position: fixed;top:0px;left:0px;width:100%;height:100%;z-index: 10;filter:alpha(opacity=90); 
-moz-opacity:0.9; 
opacity:0.9;background-color: #8E8C8C;">
	</div>
	<div id="cj"
		style="display:none;position:absolute;top:0px;left:0px;z-index: 11;padding-top:35%;">
		<img src="${ctx}activity/5_1jcpolicysale/coujiang_08.png" />
		
		<div id="immediately1" style="position:absolute;bottom:37%;left:38%; ">
			<span style="color:red;font-size:7vw;float:left;margin-top:8%;">￥</span>
			<span style="color:red;font-size:9vw;float:left;" id="moneye">0</span> <span
				style="color:red;font-size:7vw;float:left;margin-top:8%; ">元</span>
		</div>
	</div>
	<div id="cd"
		style="display:none;position:absolute;top:0px;left:0px;z-index: 11;padding-top:35%;">
		<img src="${ctx}activity/5_1jcpolicysale/coujiang_12.png" />
		<a href="${ctx}index.do?nocheck=1" style="border:0px;position: absolute;bottom:15%;left:30%;"><img src="${ctx}activity/5_1jcpolicysale/coujiang_20.png"  /></a>
	</div>
		<!-- load -->
<div class="wrap_result load" style="display:none;">
	<div class="tips" style="width: 30%;left: 55%;">
		<img src="/bxtx/images/loading_v3.gif"/>
		<p style="margin-top: -10%;font-size: 14px;">&nbsp;&nbsp;努力加载中...</p>
		<span class="loading-x"><span style="font-family:Marlett">×</span></span>
	</div>
</div>

	
	<script type="text/javascript">
		$(function(){
			$("#all").click(function(){
				$(this).hide();
				$("#cj").hide();
				$("#cd").hide();
			});
			
		})
	
		function now(){
			$(".load").show();
			$.post("/bxtx/activity/getUserPolicySalesCount.do",{},function(data){
				$("#all").show();
				if(data > 0){
					$.post("/bxtx/activity/startLuckyDraw.do",{},function(data){
						$(".load").hide();
						if(data > 0){
							$("#moneye").html(data);
							$("#cj").show();
							$("#cd").hide();
						}else{
							alert("哎呀，红包太重，没有抽出来，请重新打开。");
							
						}
					});

				}else{
					$(".load").hide();
					$("#cj").hide();
					$("#cd").show();
				}
			});

			
		}
	</script>
</body>
</html>

