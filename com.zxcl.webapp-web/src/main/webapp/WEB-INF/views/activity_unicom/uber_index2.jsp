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
<style>
.xiaotieshi{
font-size:14px;
}
.uberltlogo img:first-child{
	margin-left:8%;
}
.uberltlogo img:last-child{
	margin-right:8%;
}
.dd_index .text{
	margin-top:15px;
}
.dd_index .text .desc {
    line-height: 1em;
    padding: 2px 0;font-size:8rm;
}
.ddbox{
padding-bottom: 0px;
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
            document.write('<meta name="viewport" content="width=380, minimum-scale =0.6,initial-scale=1, maximum-scale=1, user-scalable=0, target-densitydpi=device-dpi">');
        }
    </script>
</head>
<body>
	<div class="content">
		<div class="dd_index ddbox">
			<div class="uberltlogo clear">
				<img class="lt" style="width:25%;" src="${ctx}activity/20160704unicom/uberlogo.jpg" />
				<div class="lt" style="position:absolute;margin-left:41%;margin-top:3%;border-right: 1px solid #BDBCBC;height:60%;widht:1px;"></div>
				<img class="rt" src="${ctx}images/activity/unicome/lt_logo.png" />
				
				<div class="quan q1"></div>
				<div class="quan q2"></div>
			</div>
			<div class="text">
				<!-- <div class="line2 txr c3">48元享受<span>无限</span>视频流量服务！</div> -->
				
				<div class="desc">联系乘客话费高，手机软件流量费？</div>
				<div class="desc">优步联手中国联通，打造优步车主专属超值套餐。新老用户均可办理（联通老用户办理相关规则见内页）。 </div>
				<div class="desc" style="font-weight: bold;">优惠一：话费三折起（折后29-159元）如仅需69元即可享受原166元套餐内容：含1000分钟通话，2G流量！</div>
				<div class="desc" style="font-weight: bold;">优惠二： 10元定购2G Uber定向流量，加赠腾讯视频或QQ音乐（最高赠送6G，折后99元以上套餐免费送）。</div>
				<div class="desc" style="font-weight: bold;">优惠三：关注后续活动，优步车主购买合约机，享受八折！通过授信系统，0元拿手机，不交一分钱！</div>
				<div class="desc">每位Uber VIP可有三页共60个号码可供选择。 </div>
				<div class="desc"  style="font-size:10rm;">如果您是联通用户，且未办理终端合约或参加任何赠送费活动（包括存费送费、存费送流量及单位形式赠送费活动等），即可选择Uber套餐</div>
				
				
				
				<div class="desc">还不是优步车主？长按二维码即刻注册吧</div>
				<div class="desc">
				
					<div class="img " style="width:80%;padding-left:5%;text-align: center;">
							<img style="width:8em;height:8em;" src="${ctx}activity/20160704unicom/uberrenmin1.jpg" />
					</div>
					</div>
				</div>
				
			</div>
			<div class="clear"></div>
			<div class="submit uberbc1 txc" style="line-height:2.0em;font-size:1.3em;margin:3px auto;" onclick="location.href='${ctx}activity_unicom/uber_to_phone_list.do'">开始领取</div>
		</div>
	</div>
</body>


</html>