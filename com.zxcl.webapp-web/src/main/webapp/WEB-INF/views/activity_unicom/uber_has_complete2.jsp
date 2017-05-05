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
<title>优步出行</title>
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
.uberltlogo img:first-child{
	margin-left:8%;
}
.uberltlogo img:last-child{
	margin-right:8%;
} 
.qrtj div{
font-size:1em;
}
.qrtj div b{
	font-size:1em;
}
.ubertxtbc2{
font-size:1em;
}
</style>
<script type="text/javascript">
        var phoneWidth =  parseInt(window.screen.width);
        var phoneScale = phoneWidth/380;
        var ua = navigator.userAgent;
        if (/Android (\d+\.\d+)/.test(ua)){
            var version = parseFloat(RegExp.$1);
            if(version>2.3){
                document.write('<meta name="viewport" content="width=380, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
            }else{
                document.write('<meta name="viewport" content="width=380, target-densitydpi=device-dpi">');
            }
        } else {
            document.write('<meta name="viewport" content="width=380, initial-scale=1, maximum-scale=1.001, user-scalable=0, target-densitydpi=device-dpi">');
        }
    </script>
</head>
<body>
	<div class="content">
		<div class="ddbox">
			<div class="uberltlogo clear">
				<img class="lt" style="width:25%;" src="${ctx}activity/20160704unicom/uberlogo.jpg" />
				<div class="lt" style="position:absolute;margin-left:41%;margin-top:3%;border-right: 1px solid #BDBCBC;height:60%;widht:1px;"></div>
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
		</div> 
		<div class="qrtj">
			<div>尊敬的优步用户：${unicomFlow.contactName}</div>
			<c:if test="${unicomFlow.phoneId != null && unicomFlow.phoneId != ''}">
			<div>已选号码：<b class="ubertxtbc2">${unicomFlow.phoneId}</b></div>
			</c:if>
			<div>已选套餐：<b class="ubertxtbc2">${unicomFlow.mealId}</b></div>
			<div>已选营业厅：<b class="ubertxtbc2">${unicomFlow.businessHall}</b></div>
			<div class=" clear ubertxtbc2" style="margin-top:13%;">温馨提示：</div>
			<div class="clear ubertxtbc2" >办理单卡套餐客户请在<span class="ubertxtbc2" style="font-weight: bold;">3日内</span>到营业厅办理~</div>
			<div class="clear ubertxtbc2" >办理合约机套餐客户，我们的专属客服将在<span class="ubertxtbc2" style="font-weight: bold;">1个工作日内</span>与您进行确认~</div>
			<div class="clear ubertxtbc2" ><span class="ubertxtbc2" style="font-weight: bold;">办理时间周一至周五9:00--17:30。办理时请出示您的优步车主端！</span></div>
			<div class="kefu f12 txc ubertxtbc2">联通客服电话：10010</div>
		</div>
	</div>
</body>
</html>

