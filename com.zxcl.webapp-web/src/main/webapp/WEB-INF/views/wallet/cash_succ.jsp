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
<title>保行天下-钱包提现</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css"/>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<style type="text/css">
	html, body, .wrapper {
	    background: #f2f2f2; 
        margin: 0;
        height:100%;
	}
	.money{
		color: #DB4F4F;
	    font-size: 18px;
	    font-weight: bolder;
	}
	.content-main{
		margin: 5% 5%;
		text-align: center;
	}
	.content-parent{
		margin-bottom: 8%;
		color: #8A8888;
	}
	.input-money{
		border-radius: 5px;
	    height: 30px;
	    width: 50%;
	    text-align: center;
	    font-family: Arial, Microsoft YaHei, Helvetica;
	}
	.input-money:focus{
		border:1px solid #54E6D3;
	}
	
	.may-cash-font{
		font-size: 16px;
	}
</style>
</head>
<body>
	<div class="">
		<div class="wrap_top" style="background: #DB4F4F; height:60px">
			<div class="con mauto clearfix">
				<a class="back" href="${ctx}wallet/index.do"><img src="${ctx}images/back.png"></a>
				<a class="logo bjjg">提现</a>
				<a class="tel" href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
		
		<div class="content-main">
			<div class="content-parent" style="color:#06B306;font-size: 16px;">申请成功</div>
			<div class="content-parent"><span class="may-cash-font">已提现 </span><span class="money">${amount}</span> <span class="may-cash-font">元</span></div>
			<div class="content-parent">预计2个工作日内到账</div>
		</div>
	</div>
	
	
	<jsp:include page="../commons/menu.jsp"/>
	<jsp:include page="../commons/alert.jsp"/>
	
	<script type="text/javascript" src="${ctx}js/main.js"></script>
	<script type="text/javascript">
	
	changeMenuTab(3);
	</script>
</body>














</html>