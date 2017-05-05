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
	<title>客服电话</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<script type="text/javascript">
		$(function(){
			$('.arr_r .rt').click(function(){location.href='tel:'+Trim($(this).text(),"g");});
		});
		 function Trim(str,is_global)
	        {
	            var result;
	            result = str.replace(/(^\s+)|(\s+$)/g,"");
	            if(is_global.toLowerCase()=="g")
	            {
	                result = result.replace(/\s/g,"");
	             }
	            return result;
	}
	</script>
</head>
<body>
	<div class="head"><div class="back lt" onclick="history.back();"></div><p class="title">客服电话</p></div>
	<div class="kfzx content">
		<div class="arr_r clear">
			<div class="lt">营业网点客服：</div>
			<div class="rt">${telMap.agtTeamTel }</div>
		</div>
		<div class="arr_r clear">
			<div class="lt">${agentName }客服：</div>
			<div class="rt">${telMap.agentTel }</div>
		</div>
		<div class="arr_r clear">
			<div class="lt">保行天下客服：</div>
			<div class="rt">400 9691 365</div>
		</div>
	</div>
</body>
</html>