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
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}css/style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
<style type="text/css">
.bg33c{
	    background: #51AFA0;
}
.header{ width: 100%; position:fixed;top:0;left:0;z-index:99; overflow: hidden; background: #DB4F4F}
.header .title{ width: 96%; margin: 0 auto; overflow: hidden; text-align: center; font-size: 20px; line-height: 45px; color: #FFF; font-weight: bold; }
.header .title .iconLogo{ float: left; width: 13%}
.header .title .iconTel{ float: right; width: 13%}
.header_for{height:45px;}
</style>
</head>
<body>
<header class="header">
	<div class="title">
		<a class="iconLogo"><img src="${ctx}images/iconLogo.png"></a>
		业绩报表
		<a class="iconTel" href="tel:${sessionScope.tel }"><img src="${ctx}images/iconTel.png"></a>
	</div>
</header>
	<!--主体内容 start-->
	<div class="wrapper mb15" style="margin-top: 12%;">
		<div class="chunk mb5" style="background-color: #F2F2F2;box-shadow: 0px 0px 0px #CCCCCC;">
			<div class="mauto"> 
				<div class="searchBox" style="width:92%;padding: 0 6% 3% 6%;">
					<form action="${ctx }getPerFormanceByItem.do" id="formId" method="post">
						<div>
							<input type="text" id="queryCondition" style="width: 64%;height: 30px;" value="${queryCondition}" name="queryCondition" placeholder="请输入单号或被保人"/>
							<span class="smallBtn" id="btn_query" style="margin-right: 12%;height: 26px;line-height: 26px;width: 55px;"><a>查询</a></span>
						</div>
						<div style="margin-top: 5%;">
							<input type="date" value="${beginTime }" placeholder="2015-04-05" style="width: 45%;" class="new_date" id="beginTime" name="beginTime" pattern="^\d{4}[\.|\-|\/][0|1]?\d{1}[\.|\-|\/][0-3]?\d{1}$"/>
							至
							<input type="date" value="${endTime }" placeholder="2015-04-05" style="width: 45%;" class="new_date" id="endTime" name="endTime" pattern="^\d{4}[\.|\-|\/][0|1]?\d{1}[\.|\-|\/][0-3]?\d{1}$"/>
						</div>
					</form>
				</div>
				<table class="orderList" id="tableId" cellpadding="0" cellspacing="0">
					<tr class="bg33c fcfff fb">
						<td width="36%">单号</td>
						<td width="17%">被保人</td>
						<td width="15%">提成</td>
						<td width="17%">保费</td>
						<td width="15%">操作</td>
					</tr>
					<c:forEach items="${reportList }" var="report">						
						<tr class="da">
							<td>${report.carApplyNo }</td>
							<td>${report.appinsured }</td>
							<td>${report.microComm }</td>
							<td>${report.prem }</td>
							<td><a class="smallBtn" href="${ctx }performanceInfo.do?orderId=${report.orderId }">详情</a></td>
						</tr>
					</c:forEach>
				</table>
				<br/>
			</div>
		</div>
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>
<!--<script type="text/javascript" src="js/zepto.min.js"></script>-->
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script type="text/javascript">
changeMenuTab(3);
	$(function(){
		$('#timeType').on('change',function(){
			$('#formId').submit();
		});
		$('#btn_query').live('click',function(){
//			$('#timeType').val('7');
			$('#formId').submit();
// 			var orderId = $('#orderId').val();
// 			var name = $('#name').val();
// 			var bigenTime = $('#bigenTime').val();
// 			var endTime = $('#endTime').val();
// 			var timeType = $('#timeType').val();
// 			$('.da').remove();
// 			$.ajax({
// 				type:'POST',
// 				url:'${ctx}getPerFormanceByItem.do',
// 				data:'orderId='+orderId+'&name='+name+'&bigenTime='+bigenTime+'&endTime='+endTime+'&timeType='+timeType,
// 				dataType:'json',
// 				success:function(result){
// 					var str = [];
// 					for(var o in result.data){
// 						str.push('<tr class="da">')
// 						str.push('<td>'+result.data[o].orderId+'</td>');
// 						str.push('<td>'+result.data[o].prem+'</td>');
// 						str.push('<td>'+result.data[o].microComm+'</td>');
// 						str.push('<td>'+result.data[o].appinsured+'</td>');
// 						str.push('<td><a href="${ctx }performanceInfo.do?orderId='+result.data[o].orderId+'">详情</a></td>');
// 						str.push('</tr>');
// 					}
// 					$('#tableId').append(str.join(''));
// 					str = [];
// 				}
// 			});
		});
	});
</script>
</body>
</html>