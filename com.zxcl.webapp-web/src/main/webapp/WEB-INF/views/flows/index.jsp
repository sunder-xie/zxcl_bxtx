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
%>
<!DOCTYPE html>
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
	<script type="text/javascript" src="${ctx }v3/js/TouchSlide.1.1.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/bxtx_virtual_keyboard_pink_style.css" />
	<script type="text/javascript" src="${ctx }v3/js/bxtx_virtual_keyboard.js"></script>
	
</head>
<body style="background:#fff;">
	<div class="head indexhd"><div class="lt" onclick="javascript:window.location.href='wallet/index.do'"><div class="new"></div></div><a class="tel"  href="customtel.do"><div class="rt"></div></a><p class="title text1" style="padding-right:0px;">${agentName }</p></div>
	<div class="indpag content">
		<div id="focus" class="focus" style="overflow:hidden;">
			<div class="hd"><ul><li class="">1</li><li class="">2</li><li class="on">3</li></ul></div>
			<div class="bd">
				<div class="tempWrap" style="overflow:hidden; position:relative;">
					<ul style="width:8034px; position:relative; overflow:hidden; padding:0px; margin:0px; transition:0ms; -webkit-transition:0ms; -webkit-transform:translate(-5356px, 0px) translateZ(0px);">
						<c:choose>
			        		<c:when test="${not empty activitys}">
			        			<c:forEach items="${activitys }" var="item">
			        				<li style="display:table-cell; -webkit-backface-visibility:hidden; -webkit-transform-style:preserve-3d; vertical-align:top; width:1339px;"><a style="background-image:url(${item.mainImgUrl })" href="${ctx }/activity/getActivityById.do?id=${item.id }"></a></li>
			        			</c:forEach>
			        		</c:when>
			        		<c:otherwise><li style="display:table-cell; -webkit-backface-visibility:hidden; -webkit-transform-style:preserve-3d; vertical-align:top; width:1339px;"><a style="background-image:url(${ctx}images/pic1_v2.png)" href="javascript:void(0)"></a></li></c:otherwise>
			        	</c:choose>
					</ul>
				</div>
			</div>
		</div>
		<form id="form" class="box" action="${ctx}insuranceType.do?">
			<input type="hidden" name="inquiryId" value="${inquiryId}" id="inquiryId"/>
			<input type="hidden" name="useType" id="useType" value="N"/>
			<div class="imgnav clear">
				<div class="lt" onclick='location.href="${ctx}infoQuery.do"'><img src="${ctx }v3/images/index01.png" />综合查询</div>
				<div class="lt" onclick='location.href="${ctx}list3.do"'><img src="${ctx }v3/images/index02.png" />保单查询</div>
				<div class="lt" onclick='location.href="${ctx}list2.do"'><img src="${ctx }v3/images/index03.png" />支付查询</div>
			</div>
			<p class="info c3">服务网点：${sessionScope.teamName }</p>
			<div class="car clear">
				<div class="lt img"></div>
				<input class="lt boxSet" id="province"  virtual_keyboard="province"   type="text" style="padding:0px;margin-left:3px;width:30px;    background: url('${ctx}/v3/images/arrow_r.bak1.png') no-repeat scroll right center transparent;background-size: 40%;" maxlength="1"  isMaxHidden="1"  />
				<input type="hidden" value="${protocolArea.code}" name="cityCode" />
				<input type="hidden" value="" name="plateNo" id="plateNoHidden" />
				
				<input class="lt boxSet"  virtual_keyboard="plateno"  type="text" style="width:110px;" placeholder="请输入车牌号" id="plateNo" value="" maxlength="6" pattern="^[0-9a-zA-Z]{6}$" required="required" />
				<div class="rt"><input class="ck1 ckb" id="newCarSign" type="checkbox" name="newCarSign" />未上牌</div>
			</div>
			<c:if test="${'1' eq teamSwitch }">
				<div  class="car clear" style="margin-top: 5px;">
					<div class="lt img2"></div>
					<!-- <div class="lt" style="width: 50px;height:35px;background-color: #FBFAF9;"><span style="color: #777B7E;font-size: 18px;">车主</span></div> -->
					<input style="width: 45%;height: 35px;" id="ownerName" name="ownerName" placeholder="请输入车主姓名"/>
				</div>
			</c:if>
			<p class="xy txr"><input class="ck1 ckb" type="checkbox" checked="checked" id="zx_mianze" />我已经阅读<a href="${ctx }mzsm.do" class="c3">免责声明</a></p>
			<input id="submit1" class="submit" type="button" value="下一步" /><!--  id="submit1" 填写完成后的状态 --> 
		</form>
	</div>
	<jsp:include page="../commons/menu.jsp" />
	
	<!-- 近期录入 -->
	<div class="fixedbg"></div>
	<div class="jinqilr tipsel" style="width:316px;left:45%;max-height:90%;">
		<div class="tit txc c2">近期录入<img class="close rt" src="${ctx }v3/images/close.png" /></div>
		<div class="outbox" id="jingqiluru"></div>
		<div class="qxbj bc2 txc" id="zx-ljtb">全新报价</div>
	</div>
	<script type="text/javascript">
		var ctx = "${ctx}";
		var loading;
		var loadingtime=1;
		var micStatus="${sessionScope.MICROTE_SESSION}";
		var plateNoStartWith = "${protocolArea.shortname}";
		var teamSwitch = '${teamSwitch}';
	</script>
	<script type="text/javascript" src="${ctx }v3/js/ui_index.js"></script>
	${dynamicCotent }
</body>
</html>