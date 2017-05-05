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
<script type="text/javascript" src="${ctx}v3/js/jquery.cookie.js"></script>
<link rel="stylesheet" type="text/css" href="css/v2_style.css">
<style>
th{
	text-align: center;
}
</style>
</head>
<body>
<!--横屏显示和loading start-->
<div class="cover">
        <div class="iphone"><img src="images/iphone.png"></div>
        <p>为了更好的用户体验，请将屏幕竖向浏览！</p>
    </div>
<!-- <div class="loading"> -->
<!-- 	<div class="load-container load6"> -->
<!-- 	  <div class="loader">Loading...</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!--横屏显示和loading end-->

	<!--主体内容 start-->
	<div class="wrapper mb15">
		<%-- <div class="wrap_top" style="background: #DB4F4F;">
			<div class="con mauto clearfix">
				<a class="back"><img src="${ctx}images/iconLogo.png"></a>
				<a class="logo bjjg">保行天下</a>
				<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
			</div>
		</div> --%>
		<div class="head"><div class=" lt"></div><p class="title">浮动告知单信息</p></div>
		<div style="margin:0 4%">
		<!-- 交强 -->
		<c:if test="${type eq '1' }">
		<h2 style="text-align: center; margin: 2% 0 2% 0;margin-top: 15%;" >机动车交通事故责任强制保险费率浮动告知单</h2>
			<p>投保查询码：${map.riskList[0].demandNo }</p><br/>
			<ul>
				<li>尊敬的投保人：</li>
				<li>你的机动车辆信息基本如下：</li>
				<li>车牌号码：${map.vehicle.licenseNo }</li>
				<li>发动机号：${map.vehicle.engineNo }</li>
				<li>车架号：${map.vehicle.frameNo }</li>
				<li>燃油种类：${map.vehicle.fuelValue }</li>
				<li>根据中国保险监督管理委员会批准的机动车交通事故责任强制保险费率，您的机动车交强险基础保险费是：${map.riskList[0].basePremium }</li>
				<li>交强险浮动因素计算区间：${map.riskList[0].startDate }至${map.riskList[0].endDate }</li> 
			</ul>
			<br/>
			<h3>您的机动车以往发生的机动车商业保险事故索赔记录如下：</h3>
			<table class="orderList" cellpadding="0" cellspacing="0">
				<tr class="bg33c fcfff">
					<th>序号</th>
					<th>违法记录类型</th>
					<th>详情</th>
				</tr>
				<c:forEach items="${map.peccancyList }" var="peccancy">					
					<tr>
						<td>${peccancy.serialNo }</td>
						<td>${peccancy.peccancyType }</td>
						<td><a class="smallBtn" href="javascript:info1('${peccancy.serialNo }')">详情</a></td>
					</tr>
				</c:forEach>
			</table>
			<br/>
			<ul>
				<li>根据国保险监督管理委员会公布的机动车交通事故责任强制保险费率浮动暂行办法，与道路交通事故相联系的费率浮动比例为：${map.riskProfitList[0].rate }%</li>
				<li style="color: red;">请您仔细阅读告知书类容，并点击确认，继续投保流程。</li>
				<li>本次投保的应交保险费：人民币${map.riskList[0].premium }元</li>
			</ul>
			<br/>
			<h3>您的机动车以往发生的机动车商业保险事故索赔记录如下：</h3>
			<a  id="backOnOne" class="btn">确认以上信息</a>
		</c:if>
		<!-- 商业 -->
		<c:if test="${type eq '2' }">
			<h2 style="text-align: center;margin-top: 17%;">机动车辆商业保险费率浮动告知单</h2>
			<p>投保查询码：${map.riskList[1].demandNo }</p><br/>
			<ul>
				<li>尊敬的投保人：</li>
				<li>您的机动车辆基本信息如下：</li>
				<li>车牌号码：${map.vehicle.licenseNo }</li>
				<li>厂牌车型：${map.vehicle.brandName }</li>
				<li>新车购置价：${map.vehicle.purchasePrice }</li>
				<li>商业险浮动因素计算区间：${map.riskList[1].startDate }至${map.riskList[1].endDate }</li>
			</ul>
			<table class="orderList" cellpadding="0" cellspacing="0">
				<tr class="bg33c fcfff" style="    height: 40px;">
					<th>序号</th>
					<th>出险时间</th>
					<th>详情</th>
				</tr>
				<c:forEach items="${map.payList }" var="pay">
					<tr>
						<td>${pay.serialNo }</td>
						<td>${pay.lossTime }</td>
						<td><a class="smallBtn" href="javascript:info2('${peccancy.serialNo }')">详情</a></td>
					</tr>
				</c:forEach>
			</table>	
			<br/>
			<ul>
				<li>根据中国保险监督管理委员会批准的《太平财产保险有限公司电话营销专用机动车商业保险（2012年版）条款》。</li>
				<li>您本次投保的机动车商业车险费率浮动系数值为：${map.riskProfitList[1].rate }</li>
				<li>无赔款优待及上年赔款记录浮动系数：${map.riskProfitList[2].rate }%</li>
				<li>多险种投保优惠系数：%</li>
				<li>年平均行驶里程系数：%</li>
				<li>特殊风险系数：%</li>
				<li style="color: red;">请您仔细阅读告知书内容，并点击确认，继续投保流程。</li>
			</ul>
			<a  id="backOnOne" class="btn">确认以上信息</a>
		</c:if>
		<br/>
		<br/>
		<br/>
	</div>
	</div>
	
	<div class="wrap_result dbn" id="select_result1">
		<div class="result_box">
			<div class="showBtn">
				<div class="result_top">详情</div>
				<ul style="text-align: left;">
					<li>序号：<span id="serialNo1"></span></li>
					<li>交通记录类型：<span id="peccancyType1"></span></li>
					<li>事故代码：<span id="peccancyAction1"></span></li>
					<li>违法行为次数：<span id="peccancyCount1"></span></li>
					<li>醉酒驾驶次数：<span id="drinkDrivingCount1"></span></li>
				</ul>
				<div class="result_bottom">
					<a class="btn_c" id="close_result1">确定</a>
				</div>
			</div>
		</div>
	</div>
	
	<div class="wrap_result dbn" id="select_result2">
		<div class="result_box">
			<div class="showBtn">
				<div class="result_top">详情</div>
				<ul style="text-align:left;">
					<li>序号：<span id="serialNo2"></span></li>
					<li>出险时间：<span id="lossTime2"></span></li>
					<li>结案时间：<span id="endCaseTime2"></span></li>
					<li>赔付金额：<span id="lossFee2"></span></li>
					<li>投保保险公司：<span id="payCompany2"></span></li>
				</ul>
				<div class="result_bottom">
					<a class="btn_c" id="close_result2">确定</a>
				</div>
			</div>
		</div>
	</div>
	<!--主体内容 end-->
	<!--导航部分-->
	<jsp:include page="../commons/menu.jsp"/>

<%--<script type="text/javascript" src="js/zepto.min.js"></script>--%>
<script type="text/javascript" src="js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script>
changeMenuTab(1);
function info1(serialNo){
	$.ajax({
		type:'POST',
		url:'${ctx}testOr.do',
		data:{'inquiryId':'${inquiryId }','type':'1'},
		dataType:'JSON',
		success:function(result){
			for(o in result.data){
				var json = eval('(' + result.data[o] + ')');
				$.each(json, function (name, value) {
					$('#'+name+'1').html(value);
				}); 
			}

			$('#select_result1').show();
		}
	});
	
	
	/* for(var i = 0;i < peccancyList.length;i++){
		if(serialNo == peccancyList[i].serialNo){
			$('#serialNo1').val(serialNo);
			$('#peccancyType1').val(peccancyList[i].peccancyType);
			$('#peccancyAction1').val(peccancyList[i].peccancyAction);
			$('#peccancyCount1').val(peccancyList[i].peccancyCount);
			$('#drinkDrivingCount1').val(peccancyList[i].drinkDrivingCount);
		}
	} */
}

function info2(serialNo){

	$.ajax({
		type:'POST',
		url:'${ctx}testOr.do',
		data:{'inquiryId':'${inquiryId }','type':'2'},
		dataType:'JSON',
		success:function(result){
			for(o in result.data){
				var json = eval('(' + result.data[o] + ')');
				$.each(json, function (name, value) {
					$('#'+name+'2').html(value);
				}); 
			}

			$('#select_result2').show();
		}
	});
	
	/* $('#select_result2').show();
	var peccancyList = eval('"'+'${map.payList[0] }'+'"');
	for(dto in peccancyList){
		if(serialNo == dto.serialNo){
			$('#serialNo2').val(serialNo);
			$('#lossTime2').val(dto.lossTime);
			$('#endCaseTime2').val(dto.endCaseTime);
			$('#lossFee2').val(dto.lossFee);
			$('#payCompany2').val(dto.payCompany);
		}
	} */
}

$(function(){
	 
	$('#close_result1').on('click',function(){
		$('#select_result1').hide();
	});

	$('#close_result2').on('click',function(){
		$('#select_result2').hide();
	});
	
	$('#backOnOne').on('click',function(){
		history.go(-1);
	});
	$("form").html5Validate(function() {
		//this.submit();	
	}, {
		// novalidate: false,
		// labelDrive: false
	});
	
		
})


</script>
</body></html>