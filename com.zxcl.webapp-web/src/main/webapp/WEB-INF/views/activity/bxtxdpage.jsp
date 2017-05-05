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
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
<head>
<title>保行天下</title>
<meta name="keywords" content="车险,车险报价,车险报价" />
<meta name="description"
	content="车险报价系统通过你填写资料信息进行车险报价，车险报价系统将提供多家保险公司不同的价格供您选择。" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" type="image/x-icon" href="${ctx}images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}css/v2_style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}css/common/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx }v3/css/style.css" />
<script type="text/javascript" src="${ctx}js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxc/countUp-jquery.js"></script>
<script type="text/javascript" src="${ctx}activity/bxtxb/scroll.js"></script>
<script type="text/javascript" src="${ctx }v3/js/comoperate.js"></script>
<style>
body{
	font-family:"微软雅黑";
}
.fixedbg{position:fixed; z-index:1000; top:0; bottom:0; left:0; right:0; background:rgba(0,0,0,.6); display:none;}
.input_ui {
	margin-left: -3% !important;
	margin-top: -3% !important;
}
#qiang {
	width: 60px;background-color: #a1d243;border-color: #a1d243;color: #8bc220;margin: 5px;font-size: 24px;
    height: 60px;color:white;
    line-height: 60px;border-radius: 100%;display: inline-block;cursor: pointer;
    border: none;box-sizing: border-box;    -webkit-appearance: button;    letter-spacing: normal;
    word-spacing: normal;	
    text-transform: none;
    text-indent: 0px;text-rendering: auto;-webkit-writing-mode: horizontal-tb;margin-left: 44%;
    -webkit-animation: glowing-action 3s infinite ;
    -moz-animation: glowing-action 3s infinite ;
    -ms-animation: glowing-action 3s infinite ;
    animation: glowing-action 3s infinite ;
}
#qiang:active{
	background-color: #a1d243;
    border-color: #a1d243;
    color: #8bc220;text-shadow: 0 1px 0 rgba(255, 255, 255, 0.3);
    text-decoration: none;box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.2);transition-duration: 0s;
}
@keyframes glowing-action{
	0%{    box-shadow: 0 0 0 rgba(165, 222, 55, 0.3);} 50% { box-shadow: 0 0 20px rgba(165, 222, 55, 1); } 100% {  box-shadow: 0 0 0 rgba(165, 222, 55, 0.3); }
}
@-moz-keyframes  glowing-action {
	0%{    box-shadow: 0 0 0 rgba(165, 222, 55, 0.3);} 50% { box-shadow: 0 0 20px rgba(165, 222, 55, 1); } 100% {  box-shadow: 0 0 0 rgba(165, 222, 55, 0.3); }
}

@-webkit-keyframes  glowing-action {
	0%{    box-shadow: 0 0 0 rgba(165, 222, 55, 0.3);} 50% { box-shadow: 0 0 20px rgba(165, 222, 55, 1); } 100% {  box-shadow: 0 0 0 rgba(165, 222, 55, 0.3); }
}

@-o-keyframes  glowing-action {
	0%{    box-shadow: 0 0 0 rgba(165, 222, 55, 0.3);} 50% { box-shadow: 0 0 20px rgba(165, 222, 55, 1); } 100% {  box-shadow: 0 0 0 rgba(165, 222, 55, 0.3); }
}

#qiangts {
	height:20px;width:100%;text-align:center;color:#4a4848;
}
</style>
</head>
<body>
	<div class="cover">
		<div class="iphone">
			<img src="${ctx }images/iphone.png" />
		</div>
		<p>为了更好的用户体验，请将屏幕竖向浏览！</p>
	</div>
	<div class="wrap_top" style="background:#DB4F4F;height:50px;">
		<div class="con mauto clearfix">
			<a class="back" href="javascript:history.go(-1)"><img  style="width:60%;    margin-top: 10px;"
				src="${ctx }images/back.png" /></a> <a class="logo bjjg">2016跨年盛典</a>
		</div>
	</div>
	<img src="${ctx }activity/bxtxd/1_01.jpg">
	<img src="${ctx }activity/bxtxd/1_02.jpg">
	<img src="${ctx }activity/bxtxd/1_03.jpg">
	<img src="${ctx }activity/bxtxd/1_04.jpg">
	<img src="${ctx }activity/bxtxd/1_05.jpg">
	<img src="${ctx }activity/bxtxd/1_06.jpg">
	<img src="${ctx }activity/bxtxd/1_07.jpg">
	<img src="${ctx }activity/bxtxd/1_08.jpg">
	<img src="${ctx }activity/bxtxd/1_09.jpg">
	<img src="${ctx }activity/bxtxd/1_10.jpg">
	
	<div style=";width:92.7%;height:120px;top:21%;left:0px;padding-left: 7.3%;position: absolute;">
		<div style="float:left;width:49%;height:120px;">
			<div style="color: #fdfdfc;font-weight: bold;">累计奖池<div style="clear: both;height:5px;"></div>活动时间：</div>
			<div style="margin-top:10px;color: #f3e61c;" id="ljing"></div>
		</div>
		
		<div style="float:left;width:49%;height:120px;">
			<div style="float:left;height:120px;">
			<div style="color: #fdfdfc;font-weight: bold;">领奖<div style="clear: both;height:5px;"></div>活动时间：</div>
			<div style="margin-top:10px;color: #f3e61c;" id="lqing"></div>
		</div>
		
		</div>
	</div>
	
	<div style="position: absolute;top: 42%;width:100%;" id="qiangdiv">
		<div style="color: #eb1939;font-size: 1.5rem;padding-left:16%;" id="jdts"></div>
		<div style="color: #ec1a3a;font-weight: bold; font-size: 1.5rem;text-align:center;width:100%;    margin-top: 3%;" id="cds"></div>
		<div style="color: #ec1a3a;font-size: 1.5rem;padding-left:16%;  margin-top: 3%;">累计金额：</div>
		<div style="text-align:center;width:100%;text-align:center;"><div id="money" style="display: inline-block;color: #ec1a3a;font-size: 2rem;font-weight: bold;">0.00</div><div style="display: inline-block;font-size: 0.5rem;color: #ec1a3a;padding-top: 5%;">(元)</div></div>
		<!-- <div id="qiangts">让我们抢的轰轰烈烈!</div> -->
		
	</div>
	
	<div style="position:absolute;top:72%;width:100%;width:80%;margin-left:10%;color:#ffffff;font-size:1rem;">
		<ul>
			<li style="clear: both;"><div style="float:left;width:27%;">活动时间：</div><div style="float:left;width:73%;">${timeRemark }</div></li>
			<li style="clear: both;"><div style="float:left;width:27%;">活动对象：</div><div style="float:left;width:73%;">保行天下注册用户</div></li>
			<li style="clear: both;"><div style="float:left;width:27%;">活动规则：</div><div style="float:left;width:73%;">活动期间，商业险保费每满1000元，保行天下投放<span style="color:#f8f71c;font-weight: bold;">12元</span>现金进入抽奖奖金池。注册用户报价/出第一单商业险，获得一次抢红包机会；以后每出两单，增加1次机会。金额随机，100%中奖。</div></li>
			<li style="clear: both;"><div style="float:left;width:27%;">领奖规则：</div><div style="float:left;width:73%;">详细情况请随时关注并登录平台查看</div></li>
		</ul>
	</div>
	
	<div id="qiangbtn" style="position: absolute; width: 32%;height: 13%; top: 32%; left: 34%;"></div>
	<div id="hblqopen" style=" display:none;position: absolute;top: 19%; z-index: 1001; padding-left:3.8%;    width: 96%;margin-left: 1%; height: 40%;">
		<img src="${ctx}activity/bxtxd/hbdk.png" />
	</div>
	<div id="hblqje" style=" display: none;position: absolute;top: 36%; width: 37%;left: 32%;color: #f7f71b;z-index: 1003;font-size: 2.6rem;text-align: center; font-weight: bold;">500</div>
	<div id="zlyc" style=" display:none;position: absolute;top: 47.5%;left: 34%;width: 31%; height: 3%;z-index: 1002;color:white;text-align:center;">收入钱包</div>
	
	<div id="cancel" style="display:none;background:url(${ctx}activity/bxtxc/cancel.png);position: absolute;top: 20.5%;width: 24px;height: 24px;z-index: 1004; left: 3%;"></div>
	<script type="text/javascript">
	
	var at = [{"seq":"1","st":"${startTime }","et":"${endTime }","luckSt":"${startTimeLuckStr }","luckEt":"${endTimeLuckStr }"},{"seq":"2","st":"${startTime2 }","et":"${endTime2 }","luckSt":"${startTimeLuckStr2 }","luckEt":"${endTimeLuckStr2 }"}];
	
	
	var intDiff = parseInt(60);//倒计时总秒数量
	
	
	function timer(intDiff){
		window.setInterval(function(){
		var day=0,
			hour=0,
			minute=0,
			second=0;//时间默认值		
		if(intDiff > 0){
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}
		$('#hour_show').html(hour);
		$('#minute_show').html(minute);
		$('#second_show').html(second);
		$("#cds").html( day+"天"+hour+"时"+minute+"分"+second + "秒");
		intDiff--;
		}, 1000);
	} 
	var serverTime = '${currentTime}';

	var end = undefined;
	serverTime = serverTime.replace(/-/g, "/");
	var now = new Date(serverTime);
	$(function(){
		
		//处理界面上显示的活动时间
		var a1et = new Date(at[0].et.replace(/-/g, "/"));
		var a1lst = new Date(at[0].luckSt.replace(/-/g, "/"));
		var a1let = new Date(at[0].luckEt.replace(/-/g, "/"));
		
		var a2et = new Date(at[1].et.replace(/-/g, "/"));
		var a2lst = new Date(at[1].luckSt.replace(/-/g, "/"));
		var a2let = new Date(at[1].luckEt.replace(/-/g, "/"));
		
		var qiangBtn = $("<button id=\"qiang\">抢</button>");
		qiangBtn.click(function(){
			luckdraw();
		});
		
		//控制显示活动时间
		if(now < a1et || now < a1let){
			$("#ljing").html(at[0].st + "<br/>至<br/>" + at[0].et);
			$("#lqing").html(at[0].luckSt + "<br/>至<br/>" + at[0].luckEt);
			
			//控制高亮
			if(now < a1et){
				$("#lqing").parent().css("opacity","0.7");
				$("#jdts").html("累积倒计时：");
				end = a1et;
				setTimeout("window.location.reload()",a1et - now + 1000); //时间到了。刷新页面
			}else{
				$("#ljing").parent().css("opacity","0.7");
				
				
				if(a1lst >  now){
					$("#jdts").html("等待领奖倒计时：");
					end = a1lst;
					setTimeout("window.location.reload()",a1lst - now + 1000); //时间到了。刷新页面
					
				}else if(a1let > now){
					end = a1let;
					$("#jdts").html("领奖倒计时：");
					$("#jdts").parent().css("top","39%");
					$("#cds").css("marginTop","2%");
					$("#cds").next().css("marginTop","2%");
					$("#qiangdiv").append(qiangBtn);
					setTimeout("window.location.reload()",a1let - now + 1000); //时间到了。刷新页面
				}else 
					setTimeout("window.location.reload()",a1let - now + 1000); //时间到了。刷新页面
			}
			
		}else if(now < a2et || now < a2let || a2let < now){
			$("#ljing").html(at[1].st + "<br/>至<br/>" + at[1].et);
			$("#lqing").html(at[1].luckSt + "<br/>至<br/>" + at[1].luckEt);
			//控制高亮
			if(now < a2et){
				$("#lqing").parent().css("opacity","0.7");
				$("#jdts").html("累积倒计时：");
				end = a2et;
				setTimeout("window.location.reload()",a2et - now + 1000); //时间到了。刷新页面
			}else{
				$("#ljing").parent().css("opacity","0.7");
				
				if(a2lst >  now){
					$("#jdts").html("等待领奖倒计时：");
					end = a2lst;
					setTimeout("window.location.reload()",a2lst - now + 1000); //时间到了。刷新页面
					
				}else if(a2let > now){
					end = a2let;
					$("#jdts").html("领奖倒计时：");
					$("#jdts").parent().css("top","39%");
					$("#cds").css("marginTop","2%");
					$("#cds").next().css("marginTop","2%");
					$("#qiangdiv").append(qiangBtn);
					setTimeout("window.location.reload()",a2let - now + 1000); //时间到了。刷新页面
				}else{
					$("#jdts").html("活动已结束!");$("#cds").hide();
				}
				//else 第二波的时间完了就不计时了 
					//setTimeout("window.location.reload()",a2let - now + 1000); //时间到了。刷新页面
			}
		}
		
		var time = ((end - now) / 1000);
		
		var day=0,
		hour=0,
		minute=0,
		second=0;//时间默认值		
		if(time > 0){
			day = Math.floor(time / (60 * 60 * 24));
			hour = Math.floor(time / (60 * 60)) - (day * 24);
			minute = Math.floor(time / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(time) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}
		$("#cds").html( day+"天"+hour+"时"+minute+"分"+second + "秒");
		
		timer(time);
		
		
	});	
	</script>
	<script type="text/javascript">
		var numAnim = undefined;
		$(function(){
			get();
			
			$("#zlyc,#cancel").click(function(){
				$("#hblqopen").hide();
				$("#hblqje").hide();
				$("#zlyc").hide();
				$("#hblqcjcs").hide();
				$(".fixedbg").hide();
				$("#cancel").hide();
			});
		});
	
		function getLettoryNum(){
			$.post("${ctx}/activityd/getLettoryNum.do","",function(data){
				alert(data);
			});
		}
		
		function luckdraw(){
			$.post("${ctx}/activityd/luckdraw.do",{type:"BXTX"},function(data){
				if(data.result == "-1"){
					dialog("请您先登陆","立即登陆",function(){
						window.location.reload();
					});
				}else if(data.result == "-2"){
					dialog("尚未开始抽奖，请稍等");
				}else if(data.result == "-3"){
					dialog("尚未开始抽奖，请稍等");
				}else if(data.result == "-10"){
					dialog("亲，报价/出单后才能抢红包哦");
				}else if(data.result == "-11"){
					dialog("亲，本轮抽奖次数已用完，多多报价/出单，下轮红包等您来抢~");
				}else if(data.result == "1"){
					hasfixbg();
					$(".fixedbg").show();
					$("#hblqje").html(data.amount);
					$("#hblqopen").show();
					$("#hblqje").show();
					$("#zlyc").show();
					$("#hblqcjcs").show();
					$("#cancel").show();
				}
			});
		}
		
		function get(){
			$.post("${ctx}/activityd/getCurrentMoney.do","",function(data){
				if(isNaN(parseFloat(data))){
					return ;
				}
				if(numAnim == undefined){
					numAnim = new CountUp("money", 0, data,2);
					numAnim.start();
				}else{
					numAnim.update(data);
				}
			});
			
			setTimeout("get()", 3000);
		}
	</script>
</body>
</html>

