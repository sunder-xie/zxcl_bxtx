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
<title>免责申明</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta charset="UTF-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="/bxtx/images/favicon.ico" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="full-screen" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript">
function closet(){
// 	parent.closeIframe();
window.history.go(-1);
}
</script>
<style>
.titlea{
	width:100%;
	font-size:18px;
	text-align:center;
	line-height:25px;
	margin-top:45px;
}
.dlnr{
	font-size:14px;
	font-weight: 700;
	margin-top:10px;
	line-height:25px;
	letter-spacing:2px;
	color:#0E0D0D;
}
.clear {
	clear:both;
}
.sjnr{
	font-size:12px;
	letter-spacing:1px;
	color:#333;
}
body{
    padding: 0 15px;
    overflow: scroll;
}
</style>
</head>
<body>
	<div class="head lrhd userhd">
		<div class="back lt" onclick="closet()"></div>
		<div class="rt txr"></div>
		<p class="title">保险销售服务协议</p>
	</div>
	<div id="connn">
		<div class="clear"></div>
		
		<div class="titlea"> </div>
		
		<div class="dlnr clear"></div>
		<div class="sjnr clear">&nbsp;&nbsp;&nbsp;根据《中华人民共和国合同法》、《保险代理人管理规定（试行）》等有关法律、法规，本着合规经营、服务为先、有序发展的原则，体现对代理人的关爱和负责，本公司与保险销售服务人员（以下称代理人）作如下约定。</div>
		
		
		<div class="dlnr clear">第一条  总则</div>
		<div class="sjnr clear">代理人代公司办理保险业务，在公司约定范围从事代理活动所产生的保险责任由公司所代理的保险公司承担。公司按各项业务的约定时间，支付代理人的代理手续费。</div>
		
		<div class="dlnr clear">第二条  保险费的收取和交付</div>
		<div class="sjnr clear">
			（一）代理人不得为客户垫缴保险费。
			<br/>
			（二）任何情况下，代理人均不得使用自己的姓名和账户收款。
		</div>
		
		<div class="dlnr clear">第三条  公司权利</div>
		<div class="sjnr clear">
			（一）公司或公司所代理的保险公司对代理人在授权范围内招揽的保险业务具有最后确认权限。
			<br/>
			（二）有权制定和修改各项管理办法和规章制度，随时对代理手续及其他待遇的有关规定进行修改与补充，并通知代理人。
		</div>
		
		<div class="dlnr clear">第四条  公司义务</div>
		<div class="sjnr clear">
			（一）公司按规定的手续费支付比例和时间，向代理人支付代理手续费。
			<br/>
			（二）向代理人提供开展代理业务所需的证件、资料、单证及其他的帮助。
		</div>
		
		<div class="dlnr clear">第五条  保险销售人员权利</div>
		<div class="sjnr clear">
			（一）在公司授权范围内从事保险代理活动。
			<br/>
			（二）要求公司支付代理手续费。
			<br/>
			（三）要求公司提供力所能及的有关证件、保险单证和资料。
		</div>
		
		
		<div class="dlnr clear">第六条  保险销售人员义务</div>
		<div class="sjnr clear">
			（一）应遵守公司制定的与代理人代理业务相关的规章制度。
			<br/>
			（二）按公司规定领取有关单证时履行签收手续。
			<br/>
			（三）不得与客户串通，损害公司利益。
		</div>
		
		
		
		<div style="width:100%;text-align:center;margin-top:10px;" class="clear">Copyright 2015 四川智迅车联科技股份有限公司</div>
	</div>
</body>
</html>