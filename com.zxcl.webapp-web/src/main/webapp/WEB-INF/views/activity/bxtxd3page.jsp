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
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp-jquery.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/scroll.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style>
body{
	font-family:"微软雅黑";
}
#namelists ul li div{
	display: inline-block;
	color:white;
}
#namelists ul li div:nth-child(1){
	width:42%;
}
#namelists ul li div:nth-child(2){
	width:35%;
}
</style>
</head>
<body>
	<div class="cover">
		<div class="iphone">
			<img src="${ctx }images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
	<div class="wrap_top" style="background:#DB4F4F;height:50px;">
		<div class="con mauto clearfix">
			<a class="back" href="javascript:history.go(-1)"><img  style="width:60%;    margin-top: 10px;"
				src="${ctx }images/back.png" /></a> <a class="logo bjjg">注册好运奖</a>
		</div>
	</div>
	<img src="${ctx }activity/bxtxd/a2_02.png">
	<img src="${ctx }activity/bxtxd/a2_03.png">
	<img src="${ctx }activity/bxtxd/a2_04.png">
	<img src="${ctx }activity/bxtxd/a2_05.png">
	<img src="${ctx }activity/bxtxd/a2_06.png">
	<img src="${ctx }activity/bxtxd/a2_07.png"> 
	
	<div id="namelists" style="overflow: hidden;width:70.7%;height:155px;top:51%;left:0px;padding-left: 13.3%;position: absolute;">
		<ul>
			<!-- <li><div style="width:60%;">等待统计名单，敬请期待!</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li>
			<li><div>xiaoming</div><div>第100名</div><div>￥30.00</div></li> -->
		</ul>
	</div>
	<script type="text/javascript" src="${ctx}activity/bxtxb/scroll.js"></script>
	<script>
		var data = ${namelists};
				$(function(){
					
					if(data.length > 0){
						for(var i = 0;i<data.length;i++){
							$("#namelists ul").append("<li><div>"+data[i].name+"</div><div>"+data[i].o+"</div><div>￥"+data[i].m+"</div></li>");
						}
						
						$("#namelists").myScroll({
							speed:60, //数值越大，速度越慢
							rowHeight:20 //li的高度
						});
					}else{
						$("#namelists ul").append("<li><div style=\"width:80%;\">等待统计名单，请稍后查看!</div></li>");
					}
				});
			</script>
</body>
</html>

