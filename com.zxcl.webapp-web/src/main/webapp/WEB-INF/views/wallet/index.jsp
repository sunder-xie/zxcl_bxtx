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
	<title>保行天下-我的钱包</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description" content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
	<meta charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
	<script type="text/javascript" src="${ctx }v3/js/jquery.1.7.2.min.js">
	</script><script type="text/javascript" src="${ctx }v3/js/jquery-html5Validate.js"></script>
	<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
	<style type="text/css">
		.qiandai {
			position: fixed;z-index:1000;display: none;
		}
	</style>
</head>
<body>
	<div class="head"><div class="back lt" onclick="window.location.href='${ctx}index.do?nocheck=1';"></div><p class="title">我的钱包</p></div>
	<div class="content wdqb">
		<div class="part1 clear">
			<div class="rt bc3" id="to_tixian">提现</div>
			<div class="line1">我的余额</div>
			<div class="line2 c2" id="id_amount">￥<span id="amount">0.00</span></div>
		</div>
		<div class="arr_r clear" style="background:none;background-color:#fff;"><img class="iconimg" src="${ctx}v3/images/tip03.png" />待激活金额<div class="rt" id="id_amountStay">￥0.00</div></div>
		<div class="arr_r" onclick="location.href='${ctx}wallet/bill_list.do'"><img class="iconimg" src="${ctx}v3/images/zhangdan.png" />账单明细</div>
	</div>
	<div id="redenvbg" style="position: fixed; bottom: 50px;">
		<img width="100%" src="${ctx }activity/wallte/201891-120H515115194_03.png" />
	</div>
	<jsp:include page="../commons/menu.jsp" />
	<jsp:include page="../commons/myalert2.jsp" />
	<script type="text/javascript">
		$(function(){
			setNav(3);
			
			$.post("${ctx}wallet/get_wallet.do",{},function(data){
				if(data.succ){
					$('#amount').text(parseFloat(data.data.amount).toFixed(2));
					$('#id_amountStay').text("￥" + parseFloat(data.data.amountStay).toFixed(2));
				}
			},'json');
			
			$('#to_tixian').click(function(){
				$.post("${ctx}wallet/can_cash.do",{},function(data){
					if(data.succ){
						var d = data.data;
						if("1" == d || "3" == d){
							window.location.href='${ctx}wallet_bank/index.do'
						}
						else if("2" == d){
							confirm('正在认证中','','', function(){
								return false;
							},function(){
								return false;
							});
						}
						else{
							confirm('提现需先完成实名认证','立即认证','稍后认证', function(){
								window.location.href='${ctx}identity/index.do';
							},function(){
								return false;
							});
						} 
					}
					else{
						alert(data.msg);
					}
				},'json');
			});
		});
		changeMenuTab(3);
	</script>
	
	<script type="text/javascript" src="${ctx }activity/wallte/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="${ctx }activity/wallte/jquery.fly.min.js"></script>
	<script type="text/javascript" src="${ctx }activity/wallte/requestAnimationFrame.js"></script>
	<script type="text/javascript" src="${ctx}activity/bxtxc/countUp.js"></script>
	<script type="text/javascript" src="${ctx}activity/bxtxc/countUp-jquery.js"></script>
	<script type="text/javascript" src="${ctx }activity/wallte/wallteredenv.js"></script>
</body>
</html>