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
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico"  />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css"/>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css"/> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css"/>
<style type="text/css">
	.wrap_result1{ position:fixed; width: 100%; height: 100%; top: 0; left: 0; background:rgba(0, 0, 0, .35); z-index: 999;}
	.result_box1{ position:absolute; width:90%; top:20%; left:5%; padding:0 0 5% 0; background:#FFF; z-index:1000;}
	.result_top1{ width: 100%; padding:2% 0; background:#FF6666; color: #FFF; text-align: center;}
	.result_con1{ padding: 5% 0; text-align:center;}
	.result_bottom1{ position:relative; width: 92%; padding: 13% 0; margin:0 auto; }
	.result_bottom1 a{ display: block; position: absolute; width: 45%; padding: 2% 0; background:#2ab7ec; text-align: center; color: #FFF;} 
	.result_bottom1 .btn_l{ left: 0;}
	.result_bottom1 .btn_r{ right: 0;}
</style>
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js" ></script>
</head>
<body onLoad="prettyPrint()">
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
	<div class="wrapper">
		<div class="wrap_top">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/back.png"></a>
				<a class="logo"><img src="${ctx}images/logo.png"></a>
				<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div>
		<div class="chunk mb5 quote">
<!-- 			重复投保信息显示 -->
			<div class="re_vote" id="re_vote">
				<span></span>
			</div>
			
			<div class="con mauto ">
				<h2 class="ptb2">VIP报价</h2>
				<table cellpadding="0" cellspacing="0" class="price_table">
					<tr class="bg33c fcfff fb">
						<td width="30%">公司名称</td>
						<td width="30%">保费</td>
						<td width="30%">查看明细</td>
					</tr>
					<c:forEach items="${quoteds.newAQuotasDTO}" var="quoted">
						<tr id="${quoted.insurance.insId}">
							<td>${quoted.insurance.insName}</td>
							<td class="quota_result">¥${quoted.totalAmount}</td>
							<td class="quota_result">
								<a class='smallBtn1 w75' href='premium.do?insId=${quoted.insurance.insId}&quotaId=${quoted.quotaId}&inquiryId=${quoted.inquiry.inquiryId}'>投保</a>
							</td>
						</tr>			
					</c:forEach>
<!-- 						<tr id="JTIC01"> -->
<!-- 							<td>锦泰</td> -->
<!-- 							<td class="quota_result"> -->
<%-- 								<img class="quota_loading" src="${ctx}images/status_loading.gif"/> --%>
<!-- 							</td> -->
<!-- 							<td class="quota_result"> -->
<!-- 								<a class='smallBtn1 w75' href='premium.do?insId=JTIC01&quotaId=1020101A3262015001123&inquiryId=20151116174246830'>投保</a> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 					<tr> -->
<!-- 						<td>大众保险</td> -->
<!-- 						<td>¥5000</td> -->
<!-- 						<td><a class="smallBtn w75" href="premium.do?insId=JTIC&quotaId=1020101A3262015000052">投保</a></td> -->
<!-- 					</tr> -->
				</table>
				<br/>
				<br/>
				<h2 class="ptb2">VIP报价</h2>
				<table cellpadding="0" cellspacing="0" class="price_table">
					<tr class="bg33c fcfff fb">
						<td width="30%">公司名称</td>
						<td width="30%">保费</td>
						<td width="30%">查看明细</td>
					</tr>
					<c:forEach items="${quoteds.newMQuotasDTO}" var="quoted">
						<tr id="${quoted.insurance.insId}">
							<td>${quoted.insurance.insName}</td>
							<td class="quota_result">¥${quoted.totalAmount}</td>
							<td class="quota_result">
								<a class='smallBtn1 w75' href='premium.do?insId=${quoted.insurance.insId}&quotaId=${quoted.quotaId}&inquiryId=${quoted.inquiry.inquiryId}'>投保</a>
							</td>
						</tr>			
					</c:forEach>
				</table>
			</div>
		</div>
		<!--主体内容 end-->
	</div>
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>
<!-- 	<div class="wrap_result"> -->
<!-- 		<p class="tips">loading请耐心等待...<br/>预计还有5秒</p> -->
<!-- 		<div class="mask"> -->
<!-- 			<div class="bar1"></div> -->
<!-- 			<div class="bar2"></div> -->
<!-- 			<div class="bar3"></div> -->
<!-- 			<div class="bar4"></div> -->
<!-- 			<div class="bar5"></div> -->
<!-- 			<div class="bar6"></div> -->
<!-- 			<div class="bar7"></div> -->
<!-- 			<div class="bar8"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
	<div class="wrap_result1 dbn" id="info_msg">
		<div class="result_box">
			<div class="result_top">消息提醒</div>
			<p class="result_con">您的中介公司没有合作的保险公司!!</p>
			<div class="result_bottom">
				<a class="btn_c result_close" id="result_yes">确定</a>
			</div>
		</div>	
	</div>
	<div class="wrap_result1 dbn" id="ins_info">
		<div class="result_box1">
			<div class="result_top1">商业险</div>
			<p class="result_con1 ins_info"></p>
			<div class="result_bottom1">
				<a class="btn_c result_close" id="result_yes">确定</a>
			</div>
		</div>	
	</div>

<%--<script type="text/javascript" src="js/zepto.min.js"></script>--%>
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<%-- <script type="text/javascript" src="${ctx}js/prettify.js"></script> --%>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script>
changeMenuTab(1);
$(function(){
	
	//上一页
	$(".back").on("click",function(){
		window.location.href="${cxt}insuranceType.do?inquiryId=${inquiryId}&useType=1";
	});
	//隐藏重复投保.
	$("#re_vote").hide();	
	
	var useType="${useType}";
	if("N"==useType){//需要调用接口报价
	 	//遍历循环后台的list（所有的保险公司）
		var insurances=(new Function("return "+'${insurancesJson}' ))();
		if(!insurances.length){
			$("#info_msg").show();
		}
		var inquiryId="${inquiryId}";
		for (var i = 0; i <insurances.length; i++) {
				$.ajax({
					url:"${ctx}getInsured.do?code="+insurances[i].insId+"&inquiryId="+inquiryId,
					type:"post",
					dateType:"json",
					success:function(result){
						if(result.succ){
							if(result.data.msg){
								$("#re_vote span").text(result.data.msg);
								$("#re_vote").show();
							}
							$("#"+result.data.insurance.insId).find(".quota_result").html("<a class='smallBtn1 w75' href='premium.do?insId="+result.data.insurance.insId+"&quotaId="+result.data.quotaId+"&inquiryId="+result.data.inquiryId+"'>投保</a>");
							$("#"+result.data.insurance.insId).find(".ins_price").html("¥"+result.data.amount+"");
							$("#"+result.data.insurance.insId).find(".ins_price").attr("quotaId",result.data.quotaId);
						}else{
							$("#"+result.data).find(".quota_result").text("报价失败");
						}
					}
				})
		}
	}
	
	$('#ins_info').on('click',function(){$(this).hide()});
});

//点击价格显示报价的详细信息
$(".peopleTaskOn").on("click",function(){
	var quotaId=$(this).attr("quotaId") ;
	var insId=$(this).closest("tr").attr("id");
	if(!quotaId||!insId){return;}
	$.ajax({
		type:'post',
		url:'${ctx}getIns.do',
		data:'quotaId='+quotaId+'&insId='+insId,
		dataType:'json',
		success:function(result){
			$('#ins_info').show();
			var str = [];
			str.push('<table><tr style="text-align:right"><th width="130px;">商业险</th><th width="130px;">¥'+result.data[0].vcipremTax+'</th></tr>');
			for(var o in result.data[1]){
				str.push('<tr style="text-align:right"><td>'+result.data[1][o].remark+'</td><td>¥'+result.data[1][o].amount+'</td></tr>');
			}
			str.push('</table>');
			$('.ins_info').html(str.join(''));
			str = [];
		}
	});
});
//人工报价
$(".ins_price").on("click",function(){
	var info=[];
	var checkFlag='checkis';
	var selectFlg='select';
	var inquiryId = $("#inquiryId").val(); 
	var values = new Array();
	$('input[name=check]:checked').each( function(){
		var value=new Object();
        var name=$(this).val(); 
		var options=$("."+name+" option:selected");
		value.insID=name;
		value.crtTm=options.val();
		info.push(value)
	});
	var channelIns=serializeJson(info);
	
	$.ajax({
		type:'post',
		url:'${ctx}peoplePrice.do',
		data:'inquiryId='+inquiryId+'&channelIns='+channelIns,
		dataType:'json',
		success:function(result){
		}
	});
})



$("form").html5Validate(function() {
	//alert("全部通过！");
	this.submit();	
}, {
	// novalidate: false,
	// labelDrive: false
});
</script>
</body>
</html>