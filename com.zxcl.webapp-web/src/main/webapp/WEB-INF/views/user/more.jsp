<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=utf-8"
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
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}image/favicon.ico" type="image/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/more.css?v=2">
<link rel="stylesheet" type="text/css" href="${ctx}css/login.css">
<style>
li.li-a{
    min-height: 200px;
    line-height: 200px;
    border-bottom: 0px solid #f2f2f2;
    margin-top: 4%;
}
</style>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
<!--主体内容 start-->
<header class="header">
	<div class="title">
		<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
		更多
		<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
	</div>
</header>
<div class="wrap" style="margin-bottom: 44%;">
	<section  >
		<ul class="moreList"  >
			<li><a href="${ctx}invite.do">邀请好友</a></li>
			<li><a href="${ctx}wallet/index.do">我的钱包</a></li>
			<li><a href="${ctx}manageAddress.do">配送信息管理</a></li>
			<li><a href="${ctx}used.do">使用指南</a></li>
			<li><a href="${ctx}question.do">常见问题</a></li>
			<li><a href="${ctx}about.do">关于我们</a></li>
			<li><a href="${ctx}insureSeviceTel.do">保险客服</a></li>
			<!-- <li><a href="###" onclick="check_wechat_bind()">微信</a></li> -->
			<li><a href="${ctx}revisedCode.do">修改密码</a></li>
			<%-- <li><a href="${ctx}performance.do">业绩报表</a></li> --%>
			<li><a href="${ctx}personal.do">我的信息</a></li>
			<li><a href="${ctx}message/index.do">我的消息</a></li>
			<li class="li-a">
			<button class="btn" id="exct" style="padding:0 !important;    margin: 0% auto 0 auto;">退出登录</button>
			</li>
		</ul>
	</section>
	
</div>
<div class="maskBox" style="background: rgba(0, 0, 0, .35);">
	<div class="comfirm">
		<p class="notic"><img src="${ctx}images/weixin.png"><span>绑定微信免登录</span></p>
		<div class="btns">
			<a href="javascript:void(0);" onclick="nobd()" id="bind">暂不绑定</a>
			<a href="javascript:void(0);" onclick="bd()" id="unBind">绑定</a>
		</div>
	</div>
</div>
<div class="wrap_result dbn " id="select_result">
	<div class="result_box">
		<div class="showBtn">
			<div class="result_top">提示</div>
			<p class="result_con" id="resultInfo">请使用微信客户端访问</p>
			<div class="result_bottom">
				<a class="btn_c result_close">确定</a>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../commons/menu.jsp"/>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script>
changeMenuTab(3);
$(".result_close").on("click",function(){
	$("#select_result").hide();
});
function check_wechat_bind(){
	$.post('${ctx}check_wechat_bind.do',{},function(data){
		if(data.success){
			if(data.data == 0){
				$('.maskBox').show();
			}else{
				$('.maskBox').hide();
				$('#resultInfo').text(data.message);
				$("#select_result").show();
			}
		}else{
			$('#resultInfo').text(data.message);
			$("#select_result").show();
		}
	},'json');
}
function bd(){
	if(isWeiXin()){
		window.location.href='${redrurl}';
	}else{
		$('.maskBox').hide();
		$("#select_result").show();
	}
}
function nobd(){
	$('.maskBox').hide();
}
function isWeiXin() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}

$(function(){
	$("#exct").on("click",function(){
		$.post("${ctx}outlogin.do",function(data){
			if(data.success){
				location.href="${ctx}/logon.do";
			}
		});
	});
})
</script>
</body>
</html>
