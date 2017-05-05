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
<style>
.xiaotieshi{
font-size:14px;
}
</style>
</head>
<body>
	<div class="content">
		<div class="dd_index ddbox">
			<div class="ddltlogo clear">
				<img class="lt" src="${ctx}images/activity/unicome/dd_logo.png" />
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
			<div class="text">
				<!-- <div class="line2 txr c3">48元享受<span>无限</span>视频流量服务！</div> -->
				<div class="line1">亲爱的<span class="c2">滴滴车主</span>，辛苦啦！<img class="iconimg like" src="${ctx}images/activity/unicome/like_h.png" /></div>
				
				
				<div class="desc">您还在为每月长长的话费账单烦恼吗？还在为找不到乘客却又舍不得反复打电话纠结吗？还在为开手机导航4G流量就哗哗流走心疼吗？小滴告诉您，这统统不是事儿！</div>
				<div class="desc">优惠一：话费三折起（折后29-169元） 如仅需69元即可享受原166元套餐内容：含1000分钟通话，2G流量！</div>
				<div class="desc">优惠二： 29-69套餐加10元，即赠腾讯视频或QQ音乐（最高赠送6G），折后99元以上套餐免费送！</div>
				<div class="desc">优惠三：滴滴车主购买4G合约机，享受八折！</div>
				<div class="desc">通过授信系统，0元拿手机，不交一分钱！</div>
				
				<div class="tieshi">
					<div class="xts txc">
						<div class="zi"><div class="t">小</div></div>
						<div class="zi zi1"><div class="t">贴</div><div class="zhe"></div></div>
						<div class="zi zi1"><div class="t">士</div><div class="zhe"></div></div>
					</div>
					<div class="div">
						<p class="xiaotieshi">1、中国联通4G数据传输速度可以达到上下行<span style="color:#E63636;">双百兆</span>，大成都区域4G<span style="color:#E63636;">信号全覆盖</span>；</p>
						<p class="xiaotieshi">2、语音通话时数据传输不会中断，可在<span style="color:#E63636;">通话同时极速接单</span>；</p>
						<p class="xiaotieshi">3、换号不影响司机星级和历史接单数；</p>
						<p class="xiaotieshi">4、活动时间：2016年5月20日至6月20日（门店营业时间为每周一至周五9:00-17:00）。</p>
						<div>VIP客户电话：186-0801-0160</div>
					</div>
				</div>
			</div>
			<div class="submit bc1 txc" onclick="location.href='${ctx}activity_unicom/to_phone_list.do'">开始领取</div>
		</div>
	</div>
</body>


</html>