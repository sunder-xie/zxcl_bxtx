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
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/style.css"/>
<style type="text/css">
h2 {
    color: #DB4F4F;
}
.header{ width: 100%; position:fixed;top:0;left:0;z-index:99; overflow: hidden; background: #DB4F4F}
.header .title{ width: 96%; margin: 0 auto; overflow: hidden; text-align: center; font-size: 20px; line-height: 45px; color: #FFF; font-weight: bold; }
.header .title .iconLogo{ float: left; width: 13%}
.header .title .iconTel{ float: right; width: 13%}
.header_for{height:45px;}
</style>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
	<!--主体内容 start-->
	<header class="header">
		<div class="title">
			<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
			使用指南
			<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
		</div>
	</header>
	<div class="chunk mb5" style="    margin-top: 12%;">
		<div class="con mauto">
			<h2>使用指南</h2>
			
			<h3>1.	邀请朋友注册保行天下。</h3>
				&nbsp;&nbsp;&nbsp;&nbsp;a.	小微用户可以在“更多”—“推荐好友”下找到邀请码。
				<img src="${ctx}images/used/1.png">
			<br>		
				&nbsp;&nbsp;&nbsp;&nbsp;b.	将保行天下分享给朋友，点击右上角“…”，选择“发送给朋友”。
				<img src="${ctx}images/used/2.png">
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;c.	点击朋友分享的链接，识别图片二维码。
				<img src="${ctx}images/used/3.png">
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;d.	识别二维码后，收到保行天下发送的链接。
				<img src="${ctx}images/used/4.png">
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;e.	点击链接，直接进到注册页面。
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1)	填写手机号，图片验证码。
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)	点击获取短信验证码按钮，等待短信。
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3)	填写手机收到的短信验证码。
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4)	已自动填写朋友的邀请码，直接点击下一步。
				<img src="${ctx}images/used/5.png">	
			<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5)	填写登录名、姓名、密码，确认信息无误之后点击“确认提交”按钮。
				<img src="${ctx}images/used/6.png">	
			<br>
			<h3>2.	输入账号密码登录保行天下系统。</h3>
				<img src="${ctx}images/used/7.png">
			<br>
			
			<h3>3.	输入账号密码后，出现此界面（在微信内登录才会出现），点击绑定，下次进入系统可免登陆。</h3>
				<img src="${ctx}images/used/8.png">
			<br>
			
			<h3>4.	选择省份、城市，录入车牌号，点击下一步去报价。如果是新车未上牌，请勾选“新车未上牌”。</h3>
				<img src="${ctx}images/used/9.png">
			<br>
			
			<h3>5.	录入报价信息。</h3>
			&nbsp;&nbsp;&nbsp;&nbsp;a.	录入车辆信息（按照行驶证信息录入即可）过户车勾选“过户”填写过户日期。<br>
			<img src="${ctx}images/used/10.png">
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;b.	选择交强险、商业险起保日期。录入险种信息，点击保费计算，进入投保页面。<br>
			<img src="${ctx}images/used/11.png">
			<br>
			
			<h3>6.	报价结果页面，点击按钮<img style="height: 30px;width: 30px;display: inline;" src="${ctx}images/used/12.png">，进到投保页面。</h3>
			<img src="${ctx}images/used/13.png">
			<br>
		
			<h3>7.	点击对应保险公司后面的金额查看详细险种金额，点击“立即投保”进入投保页面。</h3>	
			<img src="${ctx}images/used/14.png">
			<br>
			
			<h3>8.	录入车主信息，提交核保。</h3>
			<img src="${ctx}images/used/15.png">	
			<br>
		
			<h3>9.	核保结果，显示此信息时，表示核保已经通过了，可以支付生产保单了。</h3>
			<img src="${ctx}images/used/16.png">	
			<br>
			
			<h3>10.	支付页面，选择支付宝支付，提交订单。</h3>
			<img src="${ctx}images/used/17.png">	
			<br>
			
			<h3>11.	提交订单之后，跳转到支付宝web支付页面，输入支付宝账号、密码进行支付。</h3>
			<img src="${ctx}images/used/18.png">	
			<br>
			
			<h3>12.	支付完成，点完成，返回保行天下。</h3>
			<img src="${ctx}images/used/19.png">	
			<br>
			
			<h3>13.	生成保单。</h3>
			<img src="${ctx}images/used/20.png">	
			<br>
			
			<h3>14.	支付查询。</h3>
			&nbsp;&nbsp;&nbsp;&nbsp;a.	待支付，点击支付按钮既可以支付核保通过未支付的订单。
			<img src="${ctx}images/used/21.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;b.	待生成保单<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已支付，待生成保单的数据，点击刷新之后会去查询保险公司出单状况，如果已出单，状态会改变，生成保单。
			<img src="${ctx}images/used/22.png">	
			<br>
			
			<h3>15.	综合查询。<br>
			&nbsp;&nbsp;&nbsp;&nbsp;录单时报价暂存、已报价、核保失败、待支付的订单，都可以在这里查询到，重新进行报价、投保、支付。</h3>
			<img src="${ctx}images/used/23.png">	
			<br>
			
			<h3>16.	导航菜单下的“更多”页面。</h3>
			&nbsp;&nbsp;&nbsp;&nbsp;a.	我的信息<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击修改即可修改我的信息，修改完成之后点保存按钮保存信息。
			<img src="${ctx}images/used/24.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;b.	配送信息管理<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;添加配送信息，填写并保存信息，添加完成之后配送信息可在投保时带出配送信息。
			<img src="${ctx}images/used/25.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;c.	微信绑定<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果没有绑定微信，点击微信绑定微信，下次登录即可免登录。
			<img src="${ctx}images/used/26.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;d.	修改密码<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入原始密码，再重新输入两次新密码，点击确认修改即可。
			<img src="${ctx}images/used/27.png">	
			<br>
			
			<h3>17.	找回密码。</h3>
			&nbsp;&nbsp;&nbsp;&nbsp;a.	用户忘记密码，可在登陆页面上点击“忘记密码”。
			<img src="${ctx}images/used/28.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;b.	输入注册时的手机号和图片验证码。
			<img src="${ctx}images/used/29.png">	
			<br>
			&nbsp;&nbsp;&nbsp;&nbsp;b.	输入收到的手机验证码、重置的新密码和确认密码，点击“确认提交”。
			<img src="${ctx}images/used/30.png">	
			<br>
			
			<h3>18.	扫描二维码即可添加保行天下公众号，更多服务敬请期待。</h3>
			<img style="margin-bottom: 30%;" alt="扫描公众号" src="${ctx}images/index/index9.png">	
			<br>
		
		
		
		</div>
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>
<!--<script type="text/javascript" src="js/zepto.min.js"></script>-->
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/prettify.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
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