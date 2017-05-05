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
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="${ctx}images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}css/reset.min.css">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}css/iconfont.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css">
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/jquery.cookie.js"></script>

<style>
.tb_cell_r1{
	width: 30%;
    padding-right: 7%;
    text-align: right;
}
.money{
	color:#DB4F4F;
    font-size: 1.3em;
}
.pl30{
 	text-align: left; 
 	width: 70%;
 	padding: 0 0 0 15%;
}
h2{
    background-color: #f2f2f2;
    padding: 1% 0 1% 3%;
    color: #DB4F4F;
}
ul{
    margin: 0 3% 5% 3% !important;
}
.fcfe5 {
    color: #DB4F4F;
}
.chunk {
    padding: 0 0 3% 0;
}
.ptb2.arrow_b.clearfix.border-buttom,.ptb2.bt1e4.clearfix.border-buttom{
	padding: 2% 2%;
}
.mb5 {
    margin: 0;
}
.wrapper.mb15{
	margin-bottom: 5%;
}
table{
	margin-bottom: 5%;
	margin-left: 5%;
}
.inputClas{
	width: 65%;
}
#getValidCode{
	width: 40%;
	border-radius: 50px;
}
.head-table tr{
	height:2.2em;
}
.logo {
    left: 28.5%;
}
html, body, .wrapper {
    background: #fff;
}
.table2 tr{
	height:3.5em;
	border-bottom: 1px solid #f2f2f2;
}
.btn1 {
	position: relative;
	margin: 0 auto;
	text-align: center;
	font-family: 'Open Sans', sans-serif;
	text-decoration: none;
	color: #fff;
	border-left: solid 1px #DB4F4F;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	background-image: linear-gradient(bottom, #DB4F4F 0%, r #DB4F4F 100%);
	background-image: -webkit-linear-gradient(bottom, #DB4F4F 0%, #DB4F4F 100%);
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #DB4F4F),
		color-stop(1, #DB4F4F));
	-webkit-box-shadow: inset 0px 0px 0px #DB4F4F, -1px 6px 0px -1px #BB3C3C,
		 0px 6px 0px #BB3B3B;
    box-shadow: inset 0px 0px 0px #EA5252, -1px 6px 0px -1px #BB3C3C, 0px 6px 0px #BB3B3B;
}

.btn1:active {
	top: 3px;
	background-image: linear-gradient(bottom, #D64141 0%, #D64141 100%);
	background-image: -webkit-linear-gradient(bottom, #D64141 0%, #D64141 100%);
	background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, #D64141),
		color-stop(1, #D64141));
	-webkit-box-shadow: inset 0px 1px 0px #D64141, -1px 6px 0px -1px #D64141,
		0px 6px 0px #D64141;
	box-shadow: inset 0px 1px 0px #D64141, -1px 6px 0px -1px #D64141, 0px 6px
		0px #D64141;
}
</style>
</head>
<body>

	<!--主体内容 start-->
	<div class="wrapper mb15">
		<form action="${ctx }tpbx/payOver.do" method="post" id="formId">
			<div class="wrap_top" style="background: #DB4F4F;">
				<div class="con mauto clearfix">
					<a class="back"><img src="${ctx}images/back.png"></a>
					<a class="logo bjjg">安盛天平支付</a>
					<a class="tel"  href="tel:${sessionScope.tel }"><img src="${ctx}images/phone.png"></a>	
				</div>
			</div>
			<div class="chunk mb5">
				<div class="">
					<h2>支付信息</h2>
						<table style="    margin-bottom: 2%;   ">
							<tr><td><img style="width: 70px;70px;" src="${ctx}images/ins_logo/TPBX.png"></td>
								<td style="width: 100%; padding-top: 3%;">
									<table class="head-table">
										<tr><td>支付单号：${orderDTO.noticeNo }</td></tr>
										<tr><td>支付金额：<span class="money">${quotaDTO.totalAmount }</span></td></tr>
										<tr><td>投保人：${voteName }</td></tr>
									</table>
								</td>
							</tr>
						</table>
					<h2>银行卡信息</h2>
					<table class="table2">
						<tr>
							<td><label for="cardType"><span style="color: red;">*</span>银行卡类型</label></td>
							<td>
								<select name="cardType" id="cardType" onchange="changeInfo()">
									<option value="0001">信用卡</option>
									<option value="0002">借记卡</option>
								</select>
							</td>
						</tr>
						<tr id="expiredDate1">
							<td><label for="expiredDate"><span style="color: red;">*</span>有效期</label></td>
							<td>
								月份<select style="width: 40px;" id="monthId">
									<option value="01">1</option>
									<option value="02">2</option>
									<option value="03">3</option>
									<option value="04">4</option>
									<option value="05">5</option>
									<option value="06">6</option>
									<option value="07">7</option>
									<option value="08">8</option>
									<option value="09">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select>
								年份<select style="width: 50px;" id="yearId">
									<option value="11">2011</option>
									<option value="12">2012</option>
									<option value="13">2013</option>
									<option value="14">2014</option>
									<option value="15">2015</option>
									<option value="16">2016</option>
									<option value="17">2017</option>
									<option value="18">2018</option>
									<option value="19">2019</option>
									<option value="20">2020</option>
									<option value="21">2021</option>
								</select>
								<input type="hidden" name="expiredDate" id="expiredDate"  class="inputClas"/>
							</td>
						</tr>
						<tr id="cvv21">
							<td><label for="cvv2"><span style="color: red;">*</span>安全码</label></td>
							<td><input type="number" name="cvv2" id="cvv2" class="inputClas" placeholder="签名栏后三位数字" maxlength=3></td>
						</tr>
						<tr>
							<td><label for="cardNum"><span style="color: red;">*</span>卡号</label></td>
							<td><input type="number" name="cardNum" id="cardNum" class="inputClas" placeholder="请输入卡号" required="" pattern="(^\d{16,22}$)"></td>
						</tr>
						<tr>
							<td><label for="name"><span style="color: red;">*</span>开户名</label></td>
							<td><input type="text" name="name" id="name" class="inputClas" placeholder="请输入开户名" required=""  pattern="(^[\u4e00-\u9fa5_a-zA-Z]+$)"></td>
						</tr>
						<tr>
							<td><label for="cardHolderId"><span style="color: red;">*</span>身份证号</label></td>
							<td><input type="text" name="cardHolderId" class="inputClas" placeholder="请输入身份证号" id="cardHolderId" required="" pattern="(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)"></td>
						</tr>
						<tr>
							<td><label for="phone"><span style="color: red;">*</span>手机号</label></td>
							<td>
							<input type="tel" name="phone" id="phone" style="width: 55%;" placeholder="请输入手机号" required="" pattern="^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$">
							<span id="cleanThis"><input type="button" class="btn1"style="border-radius: 29px;padding-left: 1px;" id="getValidCode" value="获取验证码"></span>
							</td>
						</tr>
						<tr>
							<td><label for="validCode"><span style="color: red;">*</span>验证码</label></td>
							<td><input type="text" name="validCode" class="inputClas" placeholder="请输入验证码" id="validCode" required=""></td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" name="orderId" value="${orderId }">
			<input type="hidden" name="quotaId" value="${quotaDTO.quotaId }">
			<a class="btn" id="sub_order">确认支付</a>
		</form>
	</div>	
	<div class="wrap_result dbn" id="info_msg">
		<div class="result_box">
			<div class="result_top">消息提醒</div>
			<p class="result_con">获取手机验证码失败，请正确的填写数据后，重新获取</p>
			<div class="result_bottom">
				<a class="btn_c result_close" id="result_yes">确定</a>
			</div>
		</div>	
	</div>	
	<div class="wrap_result dbn" id="info_msg1">
		<div class="result_box">
			<div class="result_top">消息提醒</div>
			<p class="result_con">信用卡必须填有效期和cvv2号</p>
			<div class="result_bottom">
				<a class="btn_c result_close" id="result_yes1">确定</a>
			</div>
		</div>	
	</div>	
</body>
<script type="text/javascript" src="${ctx}js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="${ctx}js/main.js"></script>
<script type="text/javascript">
	function isOrNoNull(i){
		if(null != i && '' != i && undefined != i){
			return true;
		}else{
			return false;
		}
	}
	function changeInfo(){
		var carType = $('#cardType').val();
		if('0001' == carType){
			$('#expiredDate1').show('*');
			$('#cvv21').show();
		}else{
			$('#expiredDate1').hide('*');
			$('#cvv21').hide();
		}
	}
	$(function(){
		
		//加载年份
		var year = new Date().getFullYear()-10;
		var years = [];
		for(var i = 0;i < 20;i++){
			years.push('<option value="'+(year+i-2000)+'">'+(year+i)+'</option>');
		}
		$('#yearId').html(years.join(''));
		years = [];
		
		/*仿刷新：检测是否存在cookie*/
		var count=$.cookie("captcha");
		if(count){  var btn = $('#getValidCode');
        btn.val(count+'s').attr('disabled',true).css('cursor','not-allowed');
        var resend = setInterval(function(){
            count--;
            if (count > 0){
                btn.val(count+'s').attr('disabled',true).css('cursor','not-allowed');
                $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});
            }else {
                clearInterval(resend);
                btn.val("获取验证码").removeClass('disabled').removeAttr('disabled');
            }
        }, 1000);}
		
		$('#result_yes').on('click',function(){
			$('#info_msg').hide();
		});
		$('#result_yes1').on('click',function(){
			$('#info_msg1').hide();
		});
		//为了防止多次点击
		var date = new Array();
		$('#getValidCode').on('click',function(){
			
			//处理在一秒内多次点击
		      date.push(new Date());
		      if (date.length > 1&& (date[date.length - 1].getTime() - date[date.length - 2].getTime() < 1000)) {//小于1秒则认为重复提交
		          event.cancelBubble = true; 
		          return false;
		      }

			var cardType = $('#cardType').val();
			var expiredDate = $('#monthId').val()+$('#yearId').val();
			var cvv2 = $('#cvv2').val();
			if('0001' == cardType){
				if(!isOrNoNull(expiredDate) || !isOrNoNull(cvv2) || expiredDate.length < 4){
					$('#info_msg1').show();
					return;
				}
			}
			var cardNum = $('#cardNum').val();
			var name = $('#name').val();
			var cardHolderId = $('#cardHolderId').val();
			var phone = $('#phone').val();
			var btn = $(this);
			var count = 60;
			
			$.ajax({
				type:'POST',
				url:'${ctx}tpbx/inPay.do',
				data:{'cardNum':cardNum,'name':name,'cardHolderId':cardHolderId,'phone':phone,'orderId':'${orderId}','quotaId':'${quotaDTO.quotaId}','cardType':cardType,'expiredDate':expiredDate,'cvv2':cvv2},
				dataType:'json',
				success:function(result){
					if(!result.succ){
						$('#info_msg').show();
					}else{
						$('#expiredDate').val(expiredDate);
						var resend = setInterval(function(){
			                count--;
			                if (count > 0){
			                    btn.val(count+"s后可重新获取");
			                    $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});
			                }else {
			                    clearInterval(resend);
			                    btn.val("获取验证码").removeAttr('disabled');
			                }
			            }, 1000);
						
			            btn.attr('disabled',true).css('cursor','not-allowed');
					}
				}
			});
			
		});
		
		$('#sub_order').on('click',function(){
			$('#formId').submit();
		});
		
		$('.back').on('click',function(){
			history.go(-1);
		});
		
	});
	$("form").html5Validate(function() {
		//alert("全部通过！");
		this.submit();	
	}, {
		// novalidate: false,
		// labelDrive: false
	});
</script>
</html>