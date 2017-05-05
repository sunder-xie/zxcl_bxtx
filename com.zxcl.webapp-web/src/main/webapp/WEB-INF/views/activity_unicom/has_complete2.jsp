<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>滴滴出行</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="full-screen" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="address=no" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<script type="text/javascript" src="${ctx}js/common/TouchSlide.1.1.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}css/activity/style.css"/>
<style type="text/css">
</style>
</head>
<body>
	<div class="content">
		<div class="ddbox">
			<div class="ddltlogo clear">
				<img class="lt" src="${ctx}images/activity/unicome/dd_logo.png" />
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
		</div> 
		<div class="qrtj">
			<div>尊敬的滴滴用户：${unicomFlow.contactName}</div>
			<c:if test="${unicomFlow.phoneId != null && unicomFlow.phoneId != ''}">
			<div>已选号码：<b class="ubertxtbc2">${unicomFlow.phoneId}</b></div>
			</c:if>
			<div>已选套餐：<b class="c1">${unicomFlow.mealId}</b></div>
			<div>已选营业厅：<b>${unicomFlow.businessHall}</b></div>
			<div class=" clear" style="margin-top:3%;">平安分期助力拿手机，更快更实惠的分期方案。</div>
			<div>快长按并识别或扫描二维码来体验吧！</div>
			<div><img src="${ctx }activity/20160704unicom/pafenqishouxinerweima+.jpg" /></div>
			<div class=" clear" style="margin-top:13%;">温馨提示：</div>
			<div class="clear" style="font-size:14px;">办理单卡套餐客户请在<span class="c1">3日内</span>到营业厅办理~</div>
			<div class="clear" style="font-size:14px;">办理合约机套餐客户，我们的专属客服将在<span class="c1">1个工作日内</span>与您进行确认~</div>
			<div class="kefu f12 txc">联通客服电话：10010</div>
		</div>
	</div>
</body>
</html>

