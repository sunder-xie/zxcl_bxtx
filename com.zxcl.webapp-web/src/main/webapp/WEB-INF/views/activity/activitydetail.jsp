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
<title>${activity.title }</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />

<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/index_new.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/slidestyles.css"/>
<script type="text/javascript" src="${ctx}js/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${ctx}js/yxMobileSlider.js" ></script>

<style>
.input_ui{
	margin-left: -3% !important;
	margin-top: -3% !important;
}
</style>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
</head>
<body>
${activity.activityContent}

</body>
</html>

