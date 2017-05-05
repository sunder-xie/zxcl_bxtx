<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<jsp:include page="../commons/taglibs.jsp" /><!DOCTYPE html>
<html lang="en">
<head>
	<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">我的信息</p></div>
	<div class="content wdxx">
		<div class="part1 clear">
			<div class="lt">头像</div><div class="rt bgc"></div>
		</div>
		<div class="tit">基本信息</div>
		<!-- <div class="line arr_r clear">
			<div class="lt">性别</div>
			<select class="rt"><option>保密</option></select>
		</div> -->
		<div class="line clear">
			<div class="lt">真实姓名</div>
			<div class="rt">${micro.micro_name }</div>
		</div>
		<div class="line clear">
			<div class="lt">登录账号</div>
			<div class="rt">${userId}</div>
		</div>
		<c:if test="${not empty cardId}">
			<div class="line clear">
				<div class="lt">身份证号</div>
				<div class="rt">${cardId}</div>
			</div>
		</c:if>
		<div class="tit">所在公司</div>
		<div class="line clear">
			<div class="lt">所属团队</div>
			<div class="rt">${sessionScope.teamName }</div>
		</div>
		<!-- 
		<div class="line clear">
			<div class="lt">所在部门</div>
			<div class="rt">。。</div>
		</div> -->
	</div>
</body>
</html>