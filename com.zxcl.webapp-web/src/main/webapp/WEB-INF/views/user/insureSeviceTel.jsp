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
<link rel="stylesheet" type="text/css" href="${ctx}css/user/insureSevice.css">
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
<div class="wrap mgt20">
	<section class="insureSeviceWrap">
		<ul class="insureSeviceList">
			<li><span class="insureComName">中国人民保险</span><a href="tel:95581" class="seviceTel" tel="95581"><span>95581</span></a></li>
			<li><span class="insureComName">中国平安保险</span><a href="tel:95511" class="seviceTel" tel="95511"><span>95511</span></a></li>
			<li><span class="insureComName">中国太平洋保险</span><a href="tel:95500" class="seviceTel" tel="95500"><span>95500</span></a></li>
			<li><span class="insureComName">中国太平保险</span><a href="tel:95589" class="seviceTel" tel="95589"><span>95589</span></a></li>
			<li><span class="insureComName">中国安邦保险</span><a href="tel:95569" class="seviceTel" tel="95569"><span>95569</span></a></li>
			<li><span class="insureComName">中国永诚保险</span><a href="tel:95552" class="seviceTel" tel="95552"><span>95552</span></a></li>
			<li><span class="insureComName">中国阳光保险</span><a href="tel:95510" class="seviceTel" tel="95510"><span>95510</span></a></li>
			<li><span class="insureComName">四川锦泰保险</span><a href="tel:4008-666-555" class="seviceTel" tel="4008-666-555"><span style="margin-right: 10px;">4008-666-555</span></a></li>
			<li><span class="insureComName">安盛天平保险</span><a href="tel:95550" class="seviceTel" tel="95550"><span>95550</span></a></li>
			<li><span class="insureComName">中华财产保险</span><a href="tel:95585" class="seviceTel" tel="95585"><span>95585</span></a></li>
			<li><span class="insureComName">中国华泰保险</span><a href="tel:40060-95509" class="seviceTel" tel="40060-95509"><span style="margin-right: 10px;">40060-95509</span></a></li>
		</ul>
	</section>
	<br/>
	<br/>
	<br/>
</div>
<jsp:include page="../commons/menu.jsp"/>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script>
changeMenuTab(3);
</script>
</body>
</html>

