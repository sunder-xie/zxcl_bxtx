<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:include page="../commons/taglibs.jsp" />
	<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
	%><!DOCTYPE html>
<html lang="en">
<head>
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<link rel="stylesheet" href="${ctx}css/jquery.pagewalkthrough.css">
	<style>
		#walkthrough-content{display:none}
#walkthrough-content h3{height:30px; line-height:30px}
#walkthrough-content p{line-height:28px}
	</style>
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" ></script>
	<script type="text/javascript">
		$(function(){
			setNav(3);
		});
	</script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p id="share_top" class="title">邀请好友</p></div>
	<div class="content txc">
		<div class="yqhy">
			<img class="img" id="friend" src="${ctx }v3/images/yqhy.png" />
			<b class="line1 disb">您成功邀请<label class="c2"><c:if test="${ !empty mynum}">${mynum}</c:if>
			<c:if test="${empty mynum}">0</c:if>位</label>朋友</b>
			<div class="line2">我的邀请码：<label class="c2">${userDTO.invitation}</label></div>
			<img width="200px" height="200px" alt="邀请二维码" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticket}" />
		</div>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<div id="walkthrough-content">
	    <div id="walkthrough-1">点这里分享哦</div>
	</div>
	
	

<script type="text/javascript" src="${ctx}js/jquery.pagewalkthrough.min.js"></script>
<script type="text/javascript">
	 wx.config({
		    debug: false, 
		    appId: '${config.appId}',
		    timestamp: '${config.timestamp}',
		    nonceStr: '${config.nonceStr}',
		    signature:'${config.signature}',
		    jsApiList: [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'hideMenuItems',
				'showMenuItems',
				'hideAllNonBaseMenuItem',
				'showAllNonBaseMenuItem',
				'translateVoice',
				'startRecord',
				'stopRecord',
				'onRecordEnd',
				'playVoice',
				'pauseVoice',
				'stopVoice',
				'uploadVoice',
				'downloadVoice',
				'chooseImage',
				'previewImage',
				'uploadImage',
				'downloadImage',
				'getNetworkType',
				'openLocation',
				'getLocation',
				'hideOptionMenu',
				'showOptionMenu',
				'closeWindow',
				'scanQRCode',
				'chooseWXPay',
				'openProductSpecificView',
				'addCard',
				'chooseCard',
				'openCard'
		     ] 
		});
	 
// 	 发送给朋友: "menuItem:share:appMessage"
// 		 分享到朋友圈: "menuItem:share:timeline"
// 		 分享到QQ: "menuItem:share:qq"
// 		 分享到Weibo: "menuItem:share:weiboApp"
// 		 收藏: "menuItem:favorite"
// 		 分享到FB: "menuItem:share:facebook"
// 		 分享到 QQ 空间/menuItem:share:QZone
	 
	 wx.ready(function () {
		 wx.hideMenuItems({
			    menuList: [
			      'menuItem:share:timeline',
			      'menuItem:share:weiboApp',
			      'menuItem:favorite',
			      'menuItem:share:facebook',
			      'menuItem:share:QZone',
			      'menuItem:share:qq'
			   ]
			});
		 
			 wx.onMenuShareAppMessage({
				    title: '保行天下好友邀请', 
				    desc: '您好友邀请您加入保行天下', // 分享描述
				    link: '${url}${ctx}towCode.do?invitation='+${userDTO.invitation}, // 分享链接
				    imgUrl:'${url}${ctx}images/ios-logo.png', // 分享图标
				    type: 'link', // 分享类型,music、video或link，不填默认为link
				    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
				    success: function () { 
				        // 用户确认分享后执行的回调函数
				          alert("分享成功");
				    },
				    cancel: function () { 
				        // 用户取消分享后执行的回调函数
				        alert("分享取消");
				    }
				});	 
			 
			 
			 
			 
			 
// 	 wx.onMenuShareAppMessage({
// 		    title: '保行天下好友邀请', 
// 		    desc: '保行天下让保险更好卖', // 分享描述
// 		    link: 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1ff20874cc2cf52e&redirect_uri=${url}${ctx}addMicro.do?invitation='+${userDTO.invitation}+'&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect', // 分享链接
// 		    imgUrl:'${url}${ctx}images/bxtx_01logOld.png', // 分享图标
// 		    type: 'link', // 分享类型,music、video或link，不填默认为link
// 		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
// 		    success: function () { 
// 		        // 用户确认分享后执行的回调函数
// 		          alert("分享成功");
// 		    },
// 		    cancel: function () { 
// 		        // 用户取消分享后执行的回调函数
// 		        alert("分享取消");
// 		    }
// 		});
 	 });
	 
	 
</script>

<script>
//分享好友 
$(function() {
    // Set up tour
    $('body').pagewalkthrough({
        name: 'introduction',
        steps: [ {
            wrapper: '#share_top',
            popup: {
                content: '#walkthrough-1',
                type: 'tooltip',
                position: 'bottom'
            }
        }]
    });
    // Show the tour
    $("#friend").click(function(){
		$('body').pagewalkthrough('show');
	});
});
</script>
</body>
</html>