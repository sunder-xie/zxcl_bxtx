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
	   System.out.println(request.getRequestURI());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
		 System.out.println(request.getRequestURI());
	}
 	StringBuffer url = request.getRequestURL();
 	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();  
 	request.setAttribute("url",tempContextUrl);
%> 
<html>
<head>
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" ></script>

<style>
.simpleInfo{
    width: 105%;
    padding: 10%;
    text-indent: 25%;
    background: #fff;
        margin-top: 12%;
}
.header{ width: 100%; position:fixed;top:0;left:0;z-index:99; overflow: hidden; background: #DB4F4F}
.header .title{ width: 96%; margin: 0 auto; overflow: hidden; text-align: center; font-size: 20px; line-height: 45px; color: #FFF; font-weight: bold; }
.header .title .iconLogo{ float: left; width: 13%}
.header .title .iconTel{ float: right; width: 13%}
.header_for{height:45px;}

</style>
</head>
<body>
	<!--主体内容 start-->
	<header class="header">
		<div class="title">
			<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
			推荐好友
			<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
		</div>
	</header>
	
	<div style="width: 80%;" >
		<section class="simpleInfo">
			<p >请关注微信号再接受邀请</p>
			<p >(请长按二维码关注)</p>
		</section>
		<div class="chunk mb5" style="    width: 125%;">
			<img alt="" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticket}" />
		</div>
	</div>
	<!--主体内容 end-->
	
	</div>

</body>
</html>