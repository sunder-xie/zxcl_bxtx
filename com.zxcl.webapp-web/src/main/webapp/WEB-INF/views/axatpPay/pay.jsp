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
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<style type="text/css">
.fcfe5,h2 {
    color: #DB4F4F;
}
.asd{
	text-decoration:none;
	color: blue;
}
</style>
</head>
<body>
	<form action="${ctx }tpbx/payOver.do" method="post" id="formId">
		<table border="1">
			<tr>
				<td colspan="2">基本信息</td>
			</tr>
			<tr>
				<td><label for="validCode">手机验证码：</label></td>
				<td><input type="text" name="validCode" id="validCode" required=""></td>
			</tr>
		</table>
		<!-- 银行卡信息 -->
		<input type="hidden" name="cardNum" value="${bankCardInfoDTO.cardNum }">
		<input type="hidden" name="name" value="${bankCardInfoDTO.name }">
		<input type="hidden" name="cardHolderId" value="${bankCardInfoDTO.cardHolderId }">
		<input type="hidden" name="phone" value="${bankCardInfoDTO.phone }">
		
		<input type="hidden" name="orderId" value="${orderId }">
		<input type="hidden" name="quotaId" value="${quotaDTO.quotaId }">
	</form>	
	<a class="" id="yesPay">确定支付</a>
	<a class="" id="returnOnOne">返回上一步</a>
</body>
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$('#yesPay').on('click',function(){
			$('#formId').submit();
		});
		
		$('#returnOnOne').on('click',function(){
			history.go(-1);
		});
		
	});
	$("form").html5Validate(function() {
		//alert("全部通过！");
		this.submit();	
	}, {
		// novalidate: false,
		// labelDrive: false
	});
</script>
</html>