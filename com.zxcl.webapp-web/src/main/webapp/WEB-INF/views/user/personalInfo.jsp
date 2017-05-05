<!DOCTYPE HTML>
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
<!-- Developer:tian.kejun[田可军] date:20150620-->
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="img/favicon.ico" type="image/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/personalInfo.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body>
<img src="${ctx}images/share.png" style="position:fixed; top:-500px; opacity:0; left:0; width:320px;z-index: 0;">
<!--横屏显示和loading start-->
<div class="cover">
    <div class="iphone" ><img src="${ctx}images/iphone.png"/></div>
    <p>为了更好的用户体验，请将屏幕竖向浏览！</p>
</div>
<!-- <div class="loading"> -->
<!-- 	<div class="load-container load6"> -->
<!-- 	  <div class="loader">Loading...</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!--横屏显示和loading end-->
<!--主体内容 start-->
<header class="header">
	<div class="title">
		<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
		个人信息
		<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
	</div>
</header>
<div class="header_for"></div>
<div class="wrap">
	<form id="item_id">
		<section class="simpleInfo">
			<label>用户名：</label><span>12334354353453</span>
		</section>
		<section>
			<dl class="detailInfo">
				<dt>更多资料</dt>
				<dd>
					<ul class="detailList">
						<li><label>开<span class="half"></span>户<span class="half"></span>行：</label><input value="农业银行" type="text" placeholder="开户行"></li>
						<li><label>开<span class="half"></span>户<span class="half"></span>名：</label><input type="text" placeholder="开户名" value="张三"></li>
						<li><label>卡<span class="full"></span><span class="full"></span>号：</label><input id="test" type="text" placeholder="开卡账号" required></li>
						<li><label>手机号码：</label><input type="text" placeholder="用户手机号" value="13855555555"></li>
						<li><label>邮<span class="full"></span><span class="full"></span>箱：</label><input type="text" placeholder="邮箱"></li>
					</ul>
				</dd>
			</dl>
		</section>
		<p class="saveBtn"><button class="btn">保存</button></p>
	</form>
</div>
<footer class="footer">
		<p><a href="${ctx}index.do?nocheck=1"><img src="${ctx}images/inusre.png"></a></p>
		<p><a><img src="${ctx}images/find.png"></a></p>
		<p><a href="${ctx}more.jsp"><img src="${ctx}images/moreCurr.png"></a></p>
</footer>
<!--主体内容 end-->
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script type="text/javascript">
changeMenuTab(3);
	$("#item_id").html5Validate(function(){
	},{
		validate:function(){
			if($("#test").val().length < 5 ){
				$("#test").testRemind("okok,长度必须大于10");
				return false;
			}
			return  true;
		}
	});
</script>
</body>
</html>